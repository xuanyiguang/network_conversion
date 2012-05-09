package icm.netgen;

import icm.toplschema.Input;
import icm.toplschema.Link;
import icm.toplschema.LinkList;
import icm.toplschema.Network;
import icm.toplschema.Node;
import icm.toplschema.NodeList;
import icm.toplschema.Output;
import icm.toplschema.Parameter;
import icm.toplschema.Scenario;
import icm.toplschema.Sensor;
import icm.toplschema.SensorList;

import java.util.ArrayList;
import java.util.HashSet;

import netconfig.DataType;
import netconfig.ModelGraphLink;
import netconfig.ModelGraphNode;
import netconfig.NetconfigException;
import netconfig.TrafficFlowSink;
import netconfig.TrafficFlowSource;
import core.Monitor;

public class NetGen {

	/**
	 * Testing git functionality
	 * 
	 * @param args
	 * @throws NetconfigException
	 */
	public static void main(String[] args) throws NetconfigException {

		// Instantiate model graph based network
		// Two model graph networks are used here:
		// netHighway is of type Highway, to provide the links and nodes
		// netHybrid is of type Hybrid, to provide the ramps
		netconfig.Network netHighway = null;
		netconfig.Network netHybrid = null;
		ModelGraphNode[] modelGraphNodes = null;
		ModelGraphLink[] modelGraphLinks = null;
		netconfig.SensorPeMS[] sensorsPeMS = null;

		// instantiate the Highway network
		Monitor.set_nid(Util.nidHighway);
		netHighway = new netconfig.Network();
		Monitor.out(Util.LINE);
		Monitor.out("Highway network loaded with nid = " + Util.nidHighway);

		// obtain the model graph links, nodes, and PeMS stations
		modelGraphNodes = netHighway.getNodes();
		modelGraphLinks = netHighway.getLinks();
		sensorsPeMS = DataType.PeMS.getSensorsCached(netHighway);
		Monitor.out(Util.LINE);
		Monitor.out("Number of MM model_graph nodes: " + modelGraphNodes.length);
		Monitor.out("Number of MM model_graph links: " + modelGraphLinks.length);
		Monitor.out("Number of MM PeMS sensors: " + sensorsPeMS.length);

		// // check the max and min lane # of each link
		// // TODO: may need to divide model graph link into homogeneous links
		// // (same lane # on the link) in the future
		// Monitor.out(Util.LINE);
		// Monitor.out("link id, max lane #, min lane #, lat, lon of start node");
		// for (ModelGraphLink l : modelGraphLinks) {
		// Monitor.out(l.id + ", " + l.getMax_num_lanes() + ", "
		// + l.getMin_num_lanes() + ", " + l.getCoordinate(0).lat
		// + ", " + l.getCoordinate(0).lon);
		// }

		// these are Ethan's way to determine the node types
		ArrayList<Integer> terminalNodesID = Util.findTerminalNodesID(
				modelGraphLinks, modelGraphNodes);
		Monitor.out("Number of terminal nodes: " + terminalNodesID.size());
		ArrayList<Integer> signalNodesID = Util.findSignalNodesID(
				modelGraphLinks, modelGraphNodes);
		Monitor.out("Number of signal nodes: " + signalNodesID.size());
		ArrayList<Integer> freewayNodesID = Util.findFreewayNodesID(
				modelGraphLinks, modelGraphNodes);
		Monitor.out(Util.LINE);
		Monitor.out("Number of freeway nodes: " + freewayNodesID.size());

		// get the traffic flow sources and sinks
		Monitor.out(Util.LINE);
		TrafficFlowSource[] sources = netHighway.getTrafficFlowSources();
		Monitor.out("Number of sources: " + sources.length);
		TrafficFlowSink[] sinks = netHighway.getTrafficFlowSinks();
		Monitor.out("Number of sinks: " + sinks.length);

		// instantiate the Hybrid network
		Monitor.set_nid(Util.nidHybrid);
		netHybrid = new netconfig.Network();
		Monitor.out(Util.LINE);
		Monitor.out("Hybrid network loaded with nid = " + Util.nidHybrid);

		// sanity check on the Highway network
		Util.sanityCheck1(modelGraphLinks, modelGraphNodes);
		Util.sanityCheck2(modelGraphLinks, modelGraphNodes, netHighway);
		Util.sanityCheck3(modelGraphLinks);
		
		// Write info to TOPL network
		Scenario scenario = initializeScenario();

		// list of nodes and links
		NodeList nodeList = Util.parseModelGraphNodes(modelGraphLinks,
				modelGraphNodes, sources, sinks, netHybrid, terminalNodesID,
				signalNodesID, freewayNodesID, Util.flagSubdivisionLink);
		LinkList linkList = Util.parseModelGraphLinks(modelGraphLinks,
				modelGraphNodes, sources, sinks, netHybrid, terminalNodesID,
				Util.flagSubdivisionLink);
		Util.updateExistingNodesInputOutput(nodeList, linkList);
		SensorList sensorList = Util.parsePeMSSensors(sensorsPeMS,
				Util.flagSubdivisionLink);

		// adaptation for TOPL simulation:
		// each terminal node can only be associated with one link
		Util.oneLinkPerTerminalNode(nodeList, linkList, netHighway,
				terminalNodesID);
		Util.renameDuplicateNodes(nodeList, linkList);
		// Monitor.out(Util.LINE);
		// Monitor.out("Number of nodes modified: " +
		// nodeList.getNode().size());
		// Monitor.out("Number of links modified: " +
		// linkList.getLink().size());

		Monitor.out(Util.LINE);
		int nodeT = 0;
		int nodeF = 0;
		for (Node n : nodeList.getNode()) {
			if (n.getType().equals("F")) {
				nodeF++;
			} else if (n.getType().equals("T")) {
				nodeT++;
			}
		}
		int linkFW = 0;
		int linkOR = 0;
		int linkFR = 0;
		for (Link l : linkList.getLink()) {
			if (l.getType().equals("FW")) {
				linkFW++;
			} else if (l.getType().equals("OR")) {
				linkOR++;
			} else if (l.getType().equals("FR")) {
				linkFR++;
			}
		}
		Monitor.out("Number of TOPL nodes: " + nodeList.getNode().size());
		Monitor.out("Number of TOPL T nodes: " + nodeT);
		Monitor.out("Number of TOPL F nodes: " + nodeF);
		Monitor.out("Number of TOPL links: " + linkList.getLink().size());
		Monitor.out("Number of TOPL FW links: " + linkFW);
		Monitor.out("Number of TOPL OR links: " + linkOR);
		Monitor.out("Number of TOPL FR links: " + linkFR);
		Monitor.out("Number of TOPL sensors: " + sensorList.getSensor().size());

		Monitor.out(Util.LINE);
		if (!Util.flagSubdivisionLink) {
			int rampCount = sources.length + sinks.length
					- terminalNodesID.size();
			int tNodeCount = modelGraphNodes.length + rampCount;
			int tLinkCount = modelGraphLinks.length + rampCount;
			Monitor.out("Number of TOPL nodes (from MM): " + tNodeCount);
			Monitor.out("Number of TOPL links (from MM): " + tLinkCount);
			Monitor.out("Number of original links: " + modelGraphLinks.length);
			Monitor.out("Number of original nodes: " + modelGraphNodes.length);
			Monitor.out("Number of ramp links: " + rampCount);
		} else {
			int totalSubdivisionLink = 0;
			for (ModelGraphLink l : modelGraphLinks) {
				totalSubdivisionLink += l.nbCells;
			}
			Monitor.out("Total link subdivision: " + totalSubdivisionLink);
			int rampCount = sources.length + sinks.length
					- terminalNodesID.size();
			int tNodeCount = modelGraphNodes.length + totalSubdivisionLink
					- modelGraphLinks.length + rampCount;
			int tLinkCount = totalSubdivisionLink + rampCount;
			Monitor.out("Number of TOPL nodes (from MM): " + tNodeCount);
			Monitor.out("Number of TOPL links (from MM): " + tLinkCount);
		}
		Monitor.out("Number of PeMS sensors (from MM): " + sensorsPeMS.length);

		// sanity check on the created TOPL network
		Monitor.out(Util.LINE);
		Monitor.out("Sanity check (connectivity): "
				+ Util.sanityCheckTOPLConnectivity(nodeList, linkList));
		Monitor.out("Sanity check (sensor): "
				+ Util.sanityCheckTOPLSensors(linkList, sensorList));
		Monitor.out("Sanity check (unique node id): "
				+ Util.sanityCheckTOPLNodeUnique(nodeList));
		Monitor.out("Sanity check (unique link id): "
				+ Util.sanityCheckTOPLLinkUnique(linkList));
		Monitor.out("Sanity check (lane number from link and sensor match): "
				+ Util.sanityCheckTOPLLaneNumberMatch(linkList, sensorList));
		Monitor.out("Sanity check (short link): "
				+ Util.sanityCheckTOPLCFLCondition(linkList, scenario
						.getNetwork().getDt()));

		// connect the links, nodes and sensors back to the Network object
		scenario.getNetwork().setNodeList(nodeList);
		scenario.getNetwork().setLinkList(linkList);
		scenario.getNetwork().setSensorList(sensorList);

		// save the scenario into XML format
		scenario.saveToXML("out/" + Util.OUTPUT_FILENAME + ".xml");

		// subdivide network into south and north directions
		networkSubdivision(Util.flagSubdivisionNetwork, nodeList, linkList,
				sensorList);

		Monitor.out(Util.LINE);
		Monitor.out("Done!");

	}

	public static void networkSubdivision(boolean flagSubdivisionNetwork,
			NodeList nodeList, LinkList linkList, SensorList sensorList) {

		if (flagSubdivisionNetwork) {

			Monitor.out(Util.LINE);

			HashSet<Integer> nodeIdAll = new HashSet<Integer>();
			for (Node node : nodeList.getNode()) {
				nodeIdAll.add(Integer.parseInt(node.getId()));
			}

			HashSet<Integer> linkIdAll = new HashSet<Integer>();
			for (Link link : linkList.getLink()) {
				linkIdAll.add(Integer.parseInt(link.getId()));
			}

			// counter for # of connected groups of nodes
			int counter = 0;

			while (!linkIdAll.isEmpty()) {

				counter++;

				// nodeIdSet for the collection of node id of connected nodes
				HashSet<Integer> nodeIdSet = new HashSet<Integer>();
				// linkIdSet for the collection of link id of connected links
				HashSet<Integer> linkIdSet = new HashSet<Integer>();
				Integer linkIdSize = linkIdSet.size();

				// take one element out of linkIdAll and add to linkIdSet
				for (Integer i : linkIdAll) {
					linkIdSet.add(i);
					break;
				}

				// linkIdSize is the size before adding extra nodes
				while (linkIdSet.size() > linkIdSize) {

					// linkIdToAdd is to collect the links to be added to
					// linkIdSet in each iteration
					HashSet<Integer> linkIdToAdd = new HashSet<Integer>();
					// nodeIdToAdd is to collect the nodes to be added to
					// nodeIdSet in each iteration
					HashSet<Integer> nodeIdToAdd = new HashSet<Integer>();

					// update linkIdSize
					linkIdSize = linkIdSet.size();

					for (Integer linkID : linkIdSet) {

						// identify the link
						Link link = linkList.getLink().get(
								Util.findLinkInLinkList(linkList, linkID));

						// add its begin and end node to nodeIdToAdd
						int beginNodeId = Integer.parseInt(link.getBegin()
								.getNodeId());
						int endNodeId = Integer.parseInt(link.getEnd()
								.getNodeId());
						nodeIdToAdd.add(beginNodeId);
						nodeIdToAdd.add(endNodeId);

						// identify and add the inLinks of begin node and
						// outLinks of end node to linkIdToAdd
						Node beginNode = nodeList.getNode().get(
								Util.findNodeInNodeList(nodeList, beginNodeId));
						for (Input input : beginNode.getInputs().getInput()) {
							linkIdToAdd
									.add(Integer.parseInt(input.getLinkId()));
						}
						Node endNode = nodeList.getNode().get(
								Util.findNodeInNodeList(nodeList, endNodeId));
						for (Output output : endNode.getOutputs().getOutput()) {
							linkIdToAdd
									.add(Integer.parseInt(output.getLinkId()));
						}
					}
					linkIdSet.addAll(linkIdToAdd);
					nodeIdSet.addAll(nodeIdToAdd);
				}

				Monitor.out("Node group " + counter);
				Monitor.out("Node size: " + nodeIdSet.size());
				Monitor.out("Node id in group: " + nodeIdSet);
				Monitor.out("Link size: " + linkIdSet.size());
				Monitor.out("Link id in group: " + linkIdSet);

				// create a network with the nodes in nodeIdSet,
				// links in linkIdSet,
				// and PeMS stations on links in linkIdSet
				Scenario scenario = initializeScenario();

				NodeList nodeListSubset = new NodeList();
				for (int nodeId : nodeIdSet) {
					nodeListSubset.getNode().add(
							nodeList.getNode().get(
									Util.findNodeInNodeList(nodeList, nodeId)));
				}

				LinkList linkListSubset = new LinkList();
				for (int linkId : linkIdSet) {
					linkListSubset.getLink().add(
							linkList.getLink().get(
									Util.findLinkInLinkList(linkList, linkId)));
				}

				SensorList sensorListSubset = new SensorList();
				String highwayName = null;
				String highwayDir = null;
				for (Sensor s : sensorList.getSensor()) {
					if (linkIdSet.contains(Integer.parseInt(s.getLinks()
							.getContent()))) {
						sensorListSubset.getSensor().add(s);
						if (highwayName == null || highwayDir == null) {
							for (Parameter p : s.getParameters().getParameter()) {
								if (p.getName().equals("hwy_name")) {
									highwayName = p.getValue();
								}
								if (p.getName().equals("hwy_dir")) {
									highwayDir = p.getValue();
								}
							}
						}
					}
				}

				Monitor.out("Number of TOPL nodes in subnetwork: "
						+ nodeListSubset.getNode().size());
				Monitor.out("Number of TOPL links in subnetwork: "
						+ linkListSubset.getLink().size());
				Monitor.out("Number of TOPL sensors in subnetwork: "
						+ sensorListSubset.getSensor().size());

				scenario.getNetwork().setNodeList(nodeListSubset);
				scenario.getNetwork().setLinkList(linkListSubset);
				scenario.getNetwork().setSensorList(sensorListSubset);
				scenario.saveToXML("out/" + Util.OUTPUT_FILENAME + "_"
						+ highwayName + highwayDir + ".xml");

				// remove links/nodes in linkIdSet/nodeIdSet
				// (connected links/nodes) from linkIdAll/nodeIdAll
				linkIdAll.removeAll(linkIdSet);
				nodeIdAll.removeAll(nodeIdSet);
			}

		}
	}

	public static Scenario initializeScenario() {

		// instantiate a Scenario object
		Scenario scenario = new Scenario();
		// set attribute values for the Scenario object
		scenario.setId(Util.SCENARIO_ID);
		scenario.setName(Util.SCENARIO_NAME);
		scenario.setSchemaVersion(Util.SCENARIO_SCHEMA_VERSION);

		// instantiate a Network object
		Network network = new Network();
		// set attribute values for the Network object
		network.setId(Util.NETWORK_ID);
		network.setName(Util.NETWORK_NAME);
		network.setMlControl(Util.NETWORK_ML_CONTROL);
		network.setQControl(Util.NETWORK_Q_CONTROL);
		network.setDt(Util.NETWORK_DT);
		// connect the Network object back to the Scenario object
		scenario.setNetwork(network);

		return scenario;
	}
}
