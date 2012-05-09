package icm.netgen;

import icm.toplschema.Link;
import icm.toplschema.LinkList;
import icm.toplschema.Network;
import icm.toplschema.Node;
import icm.toplschema.NodeList;
import icm.toplschema.Scenario;
import icm.toplschema.SensorList;

import java.util.ArrayList;

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

		// Write info to TOPL network
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

		// list of nodes and links
		NodeList nodeList = Util.parseModelGraphNodes(modelGraphLinks,
				modelGraphNodes, sources, sinks, netHybrid, terminalNodesID,
				signalNodesID, freewayNodesID, Util.flagSubdivisionLink);
		LinkList linkList = Util.parseModelGraphLinks(modelGraphLinks,
				modelGraphNodes, sources, sinks, netHybrid, terminalNodesID,
				Util.flagSubdivisionLink);
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

		// connect the Network object back to the Scenario object
		network.setNodeList(nodeList);
		network.setLinkList(linkList);
		network.setSensorList(sensorList);
		scenario.setNetwork(network);

		// save the scenario into XML format
		scenario.saveToXML("out/" + Util.OUTPUT_FILENAME);

		Monitor.out(Util.LINE);
		Monitor.out("Done!");

	}
}
