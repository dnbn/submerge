package com.submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserConfiguration {

	/**
	 * Configuration for simple ass
	 */
	private AssConfiguration simpleAssConfig;

	/**
	 * Configuration for dual sub ass
	 */
	private DualSubConfiguration dualAssConfig;

	// ===================== getter and setter start =====================
	
	public AssConfiguration getSimpleAssConfig() {
		return this.simpleAssConfig;
	}

	@XmlElement(required = true)
	public void setSimpleAssConfig(AssConfiguration simpleAssConfig) {
		this.simpleAssConfig = simpleAssConfig;
	}

	public DualSubConfiguration getDualAssConfig() {
		return this.dualAssConfig;
	}

	@XmlElement(required = true)
	public void setDualAssConfig(DualSubConfiguration dualAssConfig) {
		this.dualAssConfig = dualAssConfig;
	}

}
