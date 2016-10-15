package com.submerge.web.constant;

import java.util.Locale;

/**
 * Enum all the supported locales of the application
 *
 */
public enum SupportedLocales {

	FRENCH(Locale.FRENCH), 
	ENGLISH(Locale.ENGLISH), 
	CHINESE(Locale.CHINESE);

	private Locale locale;

	SupportedLocales(Locale locale) {
		this.locale = locale;
	}
	
	public String getLanguage() {
		return this.locale.getLanguage();
	}

	public Locale getLocale() {
		return this.locale;
	}

}
