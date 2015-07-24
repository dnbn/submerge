package com.submerge.sub.config;

import java.io.Serializable;

public class SubConfig implements Serializable {

	private static final long serialVersionUID = 3329863011433285561L;

	private SubInput topSubtitle = new SubInput();
	private SubInput bottomSubtitle = new SubInput();
	private String targetFilename;

	// ===================== getter and setter start =====================

	public SubInput getTopSubtitle() {
		return this.topSubtitle;
	}

	public void setTopSubtitle(SubInput topSubtitle) {
		this.topSubtitle = topSubtitle;
	}

	public SubInput getBottomSubtitle() {
		return this.bottomSubtitle;
	}

	public void setBottomSubtitle(SubInput bottomSubtitle) {
		this.bottomSubtitle = bottomSubtitle;
	}

	public String getTargetFilename() {
		return this.targetFilename;
	}

	public void setTargetFilename(String targetFilename) {
		this.targetFilename = targetFilename;
	}

}
