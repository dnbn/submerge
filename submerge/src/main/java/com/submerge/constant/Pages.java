package com.submerge.constant;

public enum Pages {

	INDEX("index"), 
	ABOUT("about"), 
	CONTACT("contact"), 
	SIGNUP("signup"),
	ERROR("error"),

	NAVBAR("navbar", "composition/"), 
	HEADER_CONTENT("header-content", "composition/"),
	FORM_LOGIN_CONTENT("form-login-content", "composition/");
	
	private static final String ROOT = "/pages/";
	private static final String EXT = ".xhtml";
	private String name;
	private String path;

	Pages(String name) {
		this.name = name;
		this.path = "";
	}

	Pages(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public String getFullPath() {
		return ROOT + this.path + this.name + EXT;
	}

	public String getFullName() {
		return this.name + EXT;
	}

	public String getName() {
		return this.name;
	}

	public String getRedirect() {
		return this.getFullPath() + "?" + Params.REDIRECT;
	}

	@Override
	public String toString() {
		return getFullName();
	}

}
