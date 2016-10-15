package com.submerge.cli.configuration.cli;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OptionDescription {

	/**
	 * The long option nname
	 */
	private String longOption;

	/**
	 * The description
	 */
	private String description;
	
	// ===================== getter and setter start =====================

	public String getLongOption() {
		return this.longOption;
	}
	
	@XmlElement(required = true)
	public void setLongOption(String longOption) {
		this.longOption = longOption;
	}

	public String getDescription() {
		return this.description;
	}

	@XmlElement(required = true)
	public void setDescription(String description) {
		this.description = description;
	}

}
