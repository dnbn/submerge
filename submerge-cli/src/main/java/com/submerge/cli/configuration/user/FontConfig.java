//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2016.11.05 à 04:51:05 PM CET 
//


package com.submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour fontConfig complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="fontConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="color" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outlineColor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outlineWidth" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fontConfig", propOrder = {
    "color",
    "name",
    "outlineColor",
    "outlineWidth",
    "size"
})
public class FontConfig {

    @XmlElement(required = true)
    protected String color;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String outlineColor;
    protected byte outlineWidth;
    protected byte size;

    /**
     * Obtient la valeur de la propriété color.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * Définit la valeur de la propriété color.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * Obtient la valeur de la propriété name.
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
     * Définit la valeur de la propriété name.
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
     * Obtient la valeur de la propriété outlineColor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutlineColor() {
        return outlineColor;
    }

    /**
     * Définit la valeur de la propriété outlineColor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlineColor(String value) {
        this.outlineColor = value;
    }

    /**
     * Obtient la valeur de la propriété outlineWidth.
     * 
     */
    public byte getOutlineWidth() {
        return outlineWidth;
    }

    /**
     * Définit la valeur de la propriété outlineWidth.
     * 
     */
    public void setOutlineWidth(byte value) {
        this.outlineWidth = value;
    }

    /**
     * Obtient la valeur de la propriété size.
     * 
     */
    public byte getSize() {
        return size;
    }

    /**
     * Définit la valeur de la propriété size.
     * 
     */
    public void setSize(byte value) {
        this.size = value;
    }

}
