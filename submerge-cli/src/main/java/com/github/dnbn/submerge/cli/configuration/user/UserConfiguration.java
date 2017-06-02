package com.github.dnbn.submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userConfiguration", propOrder = { "dualAssConfig", "simpleAssConfig" })
public class UserConfiguration {

	@XmlElement(required = true)
	protected DualAssConfig dualAssConfig;
	@XmlElement(required = true)
	protected SubtitleConfig simpleAssConfig;

	public DualAssConfig getDualAssConfig() {
		return this.dualAssConfig;
	}

	public void setDualAssConfig(DualAssConfig value) {
		this.dualAssConfig = value;
	}

	public SubtitleConfig getSimpleAssConfig() {
		return this.simpleAssConfig;
	}

	public void setSimpleAssConfig(SubtitleConfig value) {
		this.simpleAssConfig = value;
	}

}
