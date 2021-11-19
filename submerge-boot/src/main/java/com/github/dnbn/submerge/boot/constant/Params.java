package com.github.dnbn.submerge.boot.constant;

/**
 * Requests parameters
 */
public enum Params {

	REDIRECT("faces-redirect=true");

	private String param;

	Params(String param) {
		this.param = param;
	}

	public String getParam() {
		return this.param;
	}

	@Override
	public String toString() {
		return getParam();
	}

}
