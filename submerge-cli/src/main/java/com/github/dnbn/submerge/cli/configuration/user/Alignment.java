package com.github.dnbn.submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "alignment")
@XmlEnum
public enum Alignment {

	@XmlEnumValue("centered")
	CENTERED(2),

	@XmlEnumValue("left")
	LEFT(1),

	@XmlEnumValue("right")
	RIGHT(3);

	private final int value;

	Alignment(int v) {
		this.value = v;
	}

	public int value() {
		return this.value;
	}

	public static AlignmentOffset fromValue(int v) {
		for (AlignmentOffset c : AlignmentOffset.values()) {
			if (c.value() == v) {
				return c;
			}
		}
		throw new IllegalArgumentException(Integer.toString(v));
	}

}
