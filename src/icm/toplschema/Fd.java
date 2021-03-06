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
 *       &lt;attribute name="densityCritical" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="flowMax" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="densityJam" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="capacityDrop" default="0.0">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *             &lt;minInclusive value="0.0"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "fd")
public class Fd {

    @XmlAttribute(name = "densityCritical", required = true)
    protected String densityCritical;
    @XmlAttribute(name = "flowMax", required = true)
    protected String flowMax;
    @XmlAttribute(name = "densityJam", required = true)
    protected String densityJam;
    @XmlAttribute(name = "capacityDrop")
    protected BigDecimal capacityDrop;

    /**
     * Gets the value of the densityCritical property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDensityCritical() {
        return densityCritical;
    }

    /**
     * Sets the value of the densityCritical property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDensityCritical(String value) {
        this.densityCritical = value;
    }

    /**
     * Gets the value of the flowMax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlowMax() {
        return flowMax;
    }

    /**
     * Sets the value of the flowMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlowMax(String value) {
        this.flowMax = value;
    }

    /**
     * Gets the value of the densityJam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDensityJam() {
        return densityJam;
    }

    /**
     * Sets the value of the densityJam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDensityJam(String value) {
        this.densityJam = value;
    }

    /**
     * Gets the value of the capacityDrop property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCapacityDrop() {
        if (capacityDrop == null) {
            return new BigDecimal("0.0");
        } else {
            return capacityDrop;
        }
    }

    /**
     * Sets the value of the capacityDrop property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCapacityDrop(BigDecimal value) {
        this.capacityDrop = value;
    }

}
