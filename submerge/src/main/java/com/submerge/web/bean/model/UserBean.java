package com.submerge.web.bean.model;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.model.entity.User;
import com.submerge.security.model.AuthenticatedUser;
import com.submerge.web.bean.AbstractManagedBean;

/**
 * This class is used to acces the user from views
 *
 */
@Component("userBean")
@Scope(value = "session")
public class UserBean extends AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = 3122997861016843649L;

	@Autowired
	private AuthenticatedUser user;

	private Locale locale;

	@PostConstruct
	public void init() {
		// Detect user locale
		this.locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
	}

	// ====================== public methods start =======================

	public boolean isLogged() {
		return getUser() != null;
	}

	// ===================== getter and setter start =====================

	public User getUser() {
		return this.user.getUser();
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
