//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.12 at 05:26:31 PM PDT 
//


package icm.toplschema;

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
 *       &lt;attribute name="swarm1" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="swarm2a" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="swarm2b" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "components")
public class Components {

    @XmlAttribute(name = "swarm1", required = true)
    protected boolean swarm1;
    @XmlAttribute(name = "swarm2a", required = true)
    protected boolean swarm2A;
    @XmlAttribute(name = "swarm2b", required = true)
    protected boolean swarm2B;

    /**
     * Gets the value of the swarm1 property.
     * 
     */
    public boolean isSwarm1() {
        return swarm1;
    }

    /**
     * Sets the value of the swarm1 property.
     * 
     */
    public void setSwarm1(boolean value) {
        this.swarm1 = value;
    }

    /**
     * Gets the value of the swarm2A property.
     * 
     */
    public boolean isSwarm2A() {
        return swarm2A;
    }

    /**
     * Sets the value of the swarm2A property.
     * 
     */
    public void setSwarm2A(boolean value) {
        this.swarm2A = value;
    }

    /**
     * Gets the value of the swarm2B property.
     * 
     */
    public boolean isSwarm2B() {
        return swarm2B;
    }

    /**
     * Sets the value of the swarm2B property.
     * 
     */
    public void setSwarm2B(boolean value) {
        this.swarm2B = value;
    }

}
