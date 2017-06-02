package com.github.dnbn.submerge.web.constant;

/**
 * Regexp used by the application
 * 
 */
public enum Regex {

	EMAIL("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"), 
	COLOR("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"), 
	USERNAME("^[a-z0-9_-]{5,20}$");

	private String regex;

	Regex(String regex) {

		this.regex = regex;
	}

	public String getVal() {
		return this.regex;
	}

	@Override
	public String toString() {
		return getVal();
	}
}
