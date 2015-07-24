package com.submerge.sub.config;

import java.io.Serializable;

public class Font implements Serializable {

	private static final long serialVersionUID = -3711480706383195193L;

	private String name;
	private int size = 16;
	private String color = "#fffff9";
	private String outlineColor = "#000000";
	private int outlineWidth = 2;

	// ===================== getter and setter start =====================

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getOutlineColor() {
		return this.outlineColor;
	}

	public void setOutlineColor(String outlineColor) {
		this.outlineColor = outlineColor;
	}

	public int getOutlineWidth() {
		return this.outlineWidth;
	}

	public void setOutlineWidth(int outlineWidth) {
		this.outlineWidth = outlineWidth;
	}

}
