package com.submerge.sub.config;

import java.io.Serializable;

import com.submerge.sub.itf.TimedTextFile;

public class SubInput implements Serializable {

	private static final long serialVersionUID = -485125721913729063L;

	private String styleName;
	private TimedTextFile sub;
	private Font fontconfig = new Font();
	private int alignment;

	public SubInput() {
	}

	public SubInput(TimedTextFile sub, Font fontConfig) {
		this.sub = sub;
		this.fontconfig = fontConfig;
	}

	// ===================== getter and setter start =====================

	public TimedTextFile getSub() {
		return this.sub;
	}

	public void setSub(TimedTextFile sub) {
		this.sub = sub;
	}

	public Font getFontconfig() {
		return this.fontconfig;
	}

	public void setFontconfig(Font fontconfig) {
		this.fontconfig = fontconfig;
	}

	public int getAlignment() {
		return this.alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	public String getStyleName() {
		return this.styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

}
