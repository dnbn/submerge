package com.submerge.constant;

public enum Params {

	REDIRECT("faces-redirect=true"),
	DIALOG("dialog=");

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
