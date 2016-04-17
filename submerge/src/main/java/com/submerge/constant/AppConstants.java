package com.submerge.constant;

public enum AppConstants {

	BUNDLE_RESSOURCE_BASENAME("com.submerge.i18n.text"),
	BUNDLE_RESSOURCE("msg"),
	SHA_256("SHA-256"),
	SRT("srt"),
	ASS("ass"),
	SSA("ssa"),
	UTF_8("UTF-8");
	
	final static String[] supportedLanguages = {"en", "fr", "zh"};

	private String constant;

	AppConstants(String constant) {
		this.constant = constant;
	}

	@Override
	public String toString() {
		return this.constant;
	}

}
