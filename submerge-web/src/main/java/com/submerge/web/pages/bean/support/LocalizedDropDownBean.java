package com.submerge.web.pages.bean.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.web.pages.bean.AbstractManagedBean;

@Component("localizedDropDownBean")
@Scope(value = "application")
public class LocalizedDropDownBean extends AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = -9013845270891497982L;

	/**
	 * Store all localized countries maps
	 */
	private Map<Locale, Map<Locale, String>> countries = new HashMap<>();

	/**
	 * Store all localized alignments
	 */
	private Map<Locale, Map<Integer, String>> alignments = new HashMap<>();

	/**
	 * Store all offsets localized alignments
	 */
	private Map<Locale, Map<Integer, String>> offsetAlignments = new HashMap<>();

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

	/**
	 * Get the alignment map in the user locale
	 */
	public Map<Integer, String> getAlignments() {

		Locale locale = getViewRoot().getLocale();
		Map<Integer, String> localizedAlignments = this.alignments.get(locale);

		if (localizedAlignments == null) {
			ResourceBundle bundleMessages = getBundleMessages();
			localizedAlignments = new LinkedHashMap<>();
			localizedAlignments.put(Integer.valueOf(2), bundleMessages.getString("alignment.center"));
			localizedAlignments.put(Integer.valueOf(1), bundleMessages.getString("alignment.left"));
			localizedAlignments.put(Integer.valueOf(3), bundleMessages.getString("alignment.right"));
			this.alignments.put(locale, localizedAlignments);
		}

		return localizedAlignments;
	}

	/**
	 * Get the alignment map in the user locale
	 */
	public Map<Integer, String> getOffsetAlignments() {

		Locale locale = getViewRoot().getLocale();
		Map<Integer, String> localizedOffsetAlignments = this.offsetAlignments.get(locale);

		if (localizedOffsetAlignments == null) {
			ResourceBundle bundleMessages = getBundleMessages();
			localizedOffsetAlignments = new LinkedHashMap<>();
			localizedOffsetAlignments.put(Integer.valueOf(6), bundleMessages.getString("alignment.offset.top"));
			localizedOffsetAlignments.put(Integer.valueOf(0), bundleMessages.getString("alignment.offset.bottom"));
			this.offsetAlignments.put(locale, localizedOffsetAlignments);
		}

		return localizedOffsetAlignments;
	}
}
