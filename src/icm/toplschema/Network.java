//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.12 at 05:26:31 PM PDT 
//


package icm.toplschema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}description" minOccurs="0"/>
 *         &lt;element ref="{}position" minOccurs="0"/>
 *         &lt;element ref="{}MonitorList" minOccurs="0"/>
 *         &lt;element ref="{}NetworkList" minOccurs="0"/>
 *         &lt;element ref="{}NodeList" minOccurs="0"/>
 *         &lt;element ref="{}LinkList" minOccurs="0"/>
 *         &lt;element ref="{}SignalList" minOccurs="0"/>
 *         &lt;element ref="{}ODList" minOccurs="0"/>
 *         &lt;element ref="{}SensorList" minOccurs="0"/>
 *         &lt;element ref="{}DirectionsCache" minOccurs="0"/>
 *         &lt;element ref="{}IntersectionCache" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ml_control" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="q_control" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="dt" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "network")
public class Network {

    protected String description;
    protected Position position;
    @XmlElement(name = "MonitorList")
    protected MonitorList monitorList;
    @XmlElement(name = "NetworkList")
    protected NetworkList networkList;
    @XmlElement(name = "NodeList")
    protected NodeList nodeList;
    @XmlElement(name = "LinkList")
    protected LinkList linkList;
    @XmlElement(name = "SignalList")
    protected SignalList signalList;
    @XmlElement(name = "ODList")
    protected ODList odList;
    @XmlElement(name = "SensorList")
    protected SensorList sensorList;
    @XmlElement(name = "DirectionsCache")
    protected DirectionsCache directionsCache;
    @XmlElement(name = "IntersectionCache")
    protected IntersectionCache intersectionCache;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "ml_control", required = true)
    protected boolean mlControl;
    @XmlAttribute(name = "q_control", required = true)
    protected boolean qControl;
    @XmlAttribute(name = "dt", required = true)
    protected BigDecimal dt;
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link Position }
     *     
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link Position }
     *     
     */
    public void setPosition(Position value) {
        this.position = value;
    }

    /**
     * Gets the value of the monitorList property.
     * 
     * @return
     *     possible object is
     *     {@link MonitorList }
     *     
     */
    public MonitorList getMonitorList() {
        return monitorList;
    }

    /**
     * Sets the value of the monitorList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonitorList }
     *     
     */
    public void setMonitorList(MonitorList value) {
        this.monitorList = value;
    }

    /**
     * Gets the value of the networkList property.
     * 
     * @return
     *     possible object is
     *     {@link NetworkList }
     *     
     */
    public NetworkList getNetworkList() {
        return networkList;
    }

    /**
     * Sets the value of the networkList property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetworkList }
     *     
     */
    public void setNetworkList(NetworkList value) {
        this.networkList = value;
    }

    /**
     * Gets the value of the nodeList property.
     * 
     * @return
     *     possible object is
     *     {@link NodeList }
     *     
     */
    public NodeList getNodeList() {
        return nodeList;
    }

    /**
     * Sets the value of the nodeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeList }
     *     
     */
    public void setNodeList(NodeList value) {
        this.nodeList = value;
    }

    /**
     * Gets the value of the linkList property.
     * 
     * @return
     *     possible object is
     *     {@link LinkList }
     *     
     */
    public LinkList getLinkList() {
        return linkList;
    }

    /**
     * Sets the value of the linkList property.
     * 
     * @param value
     *     allowed object is
     *     {@link LinkList }
     *     
     */
    public void setLinkList(LinkList value) {
        this.linkList = value;
    }

    /**
     * Gets the value of the signalList property.
     * 
     * @return
     *     possible object is
     *     {@link SignalList }
     *     
     */
    public SignalList getSignalList() {
        return signalList;
    }

    /**
     * Sets the value of the signalList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignalList }
     *     
     */
    public void setSignalList(SignalList value) {
        this.signalList = value;
    }

    /**
     * Gets the value of the odList property.
     * 
     * @return
     *     possible object is
     *     {@link ODList }
     *     
     */
    public ODList getODList() {
        return odList;
    }

    /**
     * Sets the value of the odList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ODList }
     *     
     */
    public void setODList(ODList value) {
        this.odList = value;
    }

    /**
     * Gets the value of the sensorList property.
     * 
     * @return
     *     possible object is
     *     {@link SensorList }
     *     
     */
    public SensorList getSensorList() {
        return sensorList;
    }

    /**
     * Sets the value of the sensorList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SensorList }
     *     
     */
    public void setSensorList(SensorList value) {
        this.sensorList = value;
    }

    /**
     * Gets the value of the directionsCache property.
     * 
     * @return
     *     possible object is
     *     {@link DirectionsCache }
     *     
     */
    public DirectionsCache getDirectionsCache() {
        return directionsCache;
    }

    /**
     * Sets the value of the directionsCache property.
     * 
     * @param value
     *     allowed object is
     *     {@link DirectionsCache }
     *     
     */
    public void setDirectionsCache(DirectionsCache value) {
        this.directionsCache = value;
    }

    /**
     * Gets the value of the intersectionCache property.
     * 
     * @return
     *     possible object is
     *     {@link IntersectionCache }
     *     
     */
    public IntersectionCache getIntersectionCache() {
        return intersectionCache;
    }

    /**
     * Sets the value of the intersectionCache property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntersectionCache }
     *     
     */
    public void setIntersectionCache(IntersectionCache value) {
        this.intersectionCache = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the mlControl property.
     * 
     */
    public boolean isMlControl() {
        return mlControl;
    }

    /**
     * Sets the value of the mlControl property.
     * 
     */
    public void setMlControl(boolean value) {
        this.mlControl = value;
    }

    /**
     * Gets the value of the qControl property.
     * 
     */
    public boolean isQControl() {
        return qControl;
    }

    /**
     * Sets the value of the qControl property.
     * 
     */
    public void setQControl(boolean value) {
        this.qControl = value;
    }

    /**
     * Gets the value of the dt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDt() {
        return dt;
    }

    /**
     * Sets the value of the dt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDt(BigDecimal value) {
        this.dt = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}