package com.github.dnbn.submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "adjustTimecodes", propOrder = { "value" })
public class AdjustTimecodes {

	@XmlValue
	protected boolean value;
	@XmlAttribute(name = "tolerance")
	protected Integer tolerance;

	public boolean getValue() {
		return this.value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public Integer getTolerance() {
		return this.tolerance;
	}

	public void setTolerance(Integer value) {
		this.tolerance = value;
	}

}
