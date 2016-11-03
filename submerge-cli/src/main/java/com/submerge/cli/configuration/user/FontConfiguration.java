package com.submerge.cli.configuration.user;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.submerge.sub.api.object.config.Font;

@XmlRootElement
public class FontConfiguration {

	/**
	 * Font configuration
	 */
	private Font fontConfig = new Font();

	// ===================== getter and setter start =====================

	public Font getFont() {
		return this.fontConfig;
	}

	// ========================= delegates start =========================

	public String getName() {

		return this.fontConfig.getName();
	}

	@XmlElement(required = true)
	public void setName(String name) {

		this.fontConfig.setName(name);
	}

	public int getSize() {

		return this.fontConfig.getSize();
	}

	@XmlElement(required = true)
	public void setSize(int size) {

		this.fontConfig.setSize(size);
	}

	public String getColor() {

		return this.fontConfig.getColor();
	}

	@XmlElement(required = true)
	public void setColor(String color) {

		this.fontConfig.setColor(color);
	}

	public String getOutlineColor() {

		return this.fontConfig.getOutlineColor();
	}

	@XmlElement(required = true)
	public void setOutlineColor(String outlineColor) {

		this.fontConfig.setOutlineColor(outlineColor);
	}

	public int getOutlineWidth() {

		return this.fontConfig.getOutlineWidth();
	}

	@XmlElement(required = true)
	public void setOutlineWidth(int outlineWidth) {

		this.fontConfig.setOutlineWidth(outlineWidth);
	}

}
