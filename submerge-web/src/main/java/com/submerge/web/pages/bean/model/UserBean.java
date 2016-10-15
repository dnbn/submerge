package com.submerge.web.pages.bean.model;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.web.constant.SupportedLocales;
import com.submerge.web.model.entity.User;
import com.submerge.web.pages.bean.AbstractManagedBean;
import com.submerge.web.pages.bean.model.proxy.AuthenticatedUser;

/**
 * This class is used to access the user from views
 *
 */
@Component("userBean")
@Scope(value = "session")
public class UserBean extends AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = 3122997861016843649L;

	@Autowired
	private AuthenticatedUser authenticatedUser;

	/**
	 * The user locale
	 */
	private Locale locale;

	@PostConstruct
	public void init() {
		Locale userLocale = SupportedLocales.ENGLISH.getLocale();

		// Detect user locale and change the actual one only if it is
		// supported by the application
		String language = getExternalContext().getRequestLocale().getLanguage();

		EnumSet<SupportedLocales> supportedLocales = EnumSet.allOf(SupportedLocales.class);
		for (SupportedLocales supportedLocale : supportedLocales) {
			if (supportedLocale.getLanguage().equals(language)) {
				userLocale = supportedLocale.getLocale();
				break;
			}
		}

		setLocale(userLocale);
	}

	// ====================== public methods start =======================

	/**
	 * Check if the user is logged
	 * 
	 * @return true is the user is logged
	 */
	public boolean isLogged() {
		return getUser() != null;
	}

	/**
	 * Update the user locale
	 */
	public void updateLocale() {
		setLanguage(getRequestParameterMap().get("languageToSet"));
		getViewRoot().setLocale(this.locale);
	}

	// ===================== getter and setter start =====================

	public User getUser() {
		return this.authenticatedUser.getUser();
	}

	public Locale getLocale() {
		return this.locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getLanguage() {
		return this.locale.getLanguage();
	}

	public void setLanguage(String language) {
		setLocale(new Locale(language));

	}

}
