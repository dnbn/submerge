package com.submerge.web.bean.support;

import java.io.Serializable;
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
	 * Fill the countries map with labels corresponding to the locale
	 */
	public Map<Object, String> getCountries() {
		Map<Object, String> countries = new LinkedHashMap<>();
		ResourceBundle bundleMessages = getBundleMessages();

		countries.put(Locale.ENGLISH, bundleMessages.getString("language.english"));
		countries.put(Locale.SIMPLIFIED_CHINESE, bundleMessages.getString("language.chinese"));
		countries.put(Locale.FRENCH, bundleMessages.getString("language.french"));
		return countries;
	}

}
