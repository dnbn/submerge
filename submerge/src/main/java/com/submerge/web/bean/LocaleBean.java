package com.submerge.web.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.constant.AppConstants;

@Component("localeBean")
@Scope(value = "session")
public class LocaleBean implements Serializable {

	private static final long serialVersionUID = -9013845270891497982L;

	/**
	 * Supported countries (language)
	 */
	private Map<Object, String> countries;

	/**
	 * Locale selected by user or auto-selected
	 */
	private Locale locale;

	@PostConstruct
	public void init() {
		// Detect user locale
		this.locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
		initCountries();
	}

	/**
	 * Fill the countries map with labels corresponding to the locale
	 */
	private void initCountries() {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, AppConstants.BUNDLE_RESSOURCE.toString());

		this.countries = new LinkedHashMap<>();
		this.countries.put(Locale.ENGLISH, bundle.getString("language.english"));
		this.countries.put(Locale.SIMPLIFIED_CHINESE, bundle.getString("language.chinese"));
		this.countries.put(Locale.FRENCH, bundle.getString("language.french"));
	}

	// ===================== getter and setter start =====================

	public Locale getLocale() {
		return this.locale;
	}

	public String getLanguage() {
		return this.locale.getLanguage();
	}

	public void setLanguage(String language) {
		this.locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
		initCountries();
	}

	public Map<Object, String> getCountries() {
		return this.countries;
	}

}
