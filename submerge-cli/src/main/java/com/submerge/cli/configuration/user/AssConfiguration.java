package com.submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AssConfiguration {

	/**
	 * Style name
	 */
	private String styleName;

	/**
	 * Font configuration
	 */
	private FontConfiguration fontConfig;

	public String getStyleName() {

		return this.styleName;
	}
	
	// ===================== getter and setter start =====================

	@XmlElement(required = true)
	public void setStyleName(String styleName) {

		this.styleName = styleName;
	}

	public FontConfiguration getFontConfig() {

		return this.fontConfig;
	}

	@XmlElement(required = true)
	public void setFontConfig(FontConfiguration fontConfig) {

		this.fontConfig = fontConfig;
	}

}
