package com.github.dnbn.submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import com.github.dnbn.submerge.api.subtitle.config.Font;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subtitleConfig", propOrder = { 
		"fontConfig", 
		"styleName", 
		"alignment", 
		"alignmentOffset" 
})
public class SubtitleConfig {

	@XmlElement(required = true)
	protected Font fontConfig;
	@XmlElement(required = true)
	protected String styleName;
	@XmlElement(required = true)
	@XmlSchemaType(name = "string")
	protected Alignment alignment;
	@XmlElement(required = true)
	@XmlSchemaType(name = "string")
	protected AlignmentOffset alignmentOffset;

	public Font getFontConfig() {
		return this.fontConfig;
	}

	public void setFontConfig(Font value) {
		this.fontConfig = value;
	}

	public String getStyleName() {
		return this.styleName;
	}

	public void setStyleName(String value) {
		this.styleName = value;
	}

	public Alignment getAlignment() {
		return this.alignment;
	}

	public void setAlignment(Alignment value) {
		this.alignment = value;
	}

	public AlignmentOffset getAlignmentOffset() {
		return this.alignmentOffset;
	}

	public void setAlignmentOffset(AlignmentOffset value) {
		this.alignmentOffset = value;
	}

}
