package com.submerge.sub.config;

import java.io.Serializable;

import com.submerge.sub.srt.SRTSub;

public class SubInput implements Serializable {

	private static final long serialVersionUID = -485125721913729063L;

	private String styleName;
	private SRTSub sub;
	private Font fontconfig = new Font();
	private int alignment;

	public SubInput() {
	}
	
	public SubInput(SRTSub sub, Font fontConfig) {
		this.sub = sub;
		this.fontconfig = fontConfig;
	}

	// ===================== getter and setter start =====================

	public SRTSub getSub() {
		return this.sub;
	}

	public void setSub(SRTSub sub) {
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
