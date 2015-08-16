package com.submerge.web.bean.model;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.model.entity.User;
import com.submerge.web.bean.AbstractManagedBean;
import com.submerge.web.bean.model.proxy.AuthenticatedUser;

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

	private Locale locale;

	@PostConstruct
	public void init() {
		// Detect user locale
		this.locale = new Locale(getExternalContext().getRequestLocale().getLanguage());
	}

	// ====================== public methods start =======================

	public boolean isLogged() {
		return getUser() != null;
	}

	public void updateLocale() {
		setLanguage(getRequestParameterMap().get("languageToSet"));
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
		this.locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
	}

}
