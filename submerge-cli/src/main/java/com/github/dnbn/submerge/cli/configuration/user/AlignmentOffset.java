package com.github.dnbn.submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "alignmentOffset")
@XmlEnum
public enum AlignmentOffset {

	@XmlEnumValue("top")
	TOP(6),

	@XmlEnumValue("bottom")
	BOTTOM(0);

	private final int value;

	AlignmentOffset(int v) {
		this.value = v;

	}

	public int value() {
		return this.value;
	}

	public static Alignment fromValue(int v) {
		for (Alignment c : Alignment.values()) {
			if (c.value() == v) {
				return c;
			}
		}
		throw new IllegalArgumentException(Integer.toString(v));
	}

}
