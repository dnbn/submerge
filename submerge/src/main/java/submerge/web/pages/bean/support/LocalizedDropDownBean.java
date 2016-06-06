package com.submerge.web.bean.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.web.bean.AbstractManagedBean;

@Component("localizedDropDownBean")
@Scope(value = "application")
public class LocalizedDropDownBean extends AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = -9013845270891497982L;

	/**
	 * Store all localized countries maps
	 */
	private Map<Locale, Map<Locale, String>> countries = new HashMap<>();

	/**
	 * Get the country map in the user locale
	 */
	public Map<Locale, String> getCountries() {
		Locale locale = getViewRoot().getLocale();
		Map<Locale, String> localizedCountries = this.countries.get(locale);

		if (localizedCountries == null) {
			ResourceBundle bundleMessages = getBundleMessages();
			localizedCountries = new LinkedHashMap<>();
			localizedCountries.put(Locale.ENGLISH, bundleMessages.getString("language.english"));
			localizedCountries.put(Locale.CHINESE, bundleMessages.getString("language.chinese"));
			localizedCountries.put(Locale.FRENCH, bundleMessages.getString("language.french"));
			this.countries.put(locale, localizedCountries);
		}

		return localizedCountries;
	}

}
