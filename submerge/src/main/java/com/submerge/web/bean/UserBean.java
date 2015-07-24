package com.submerge.web.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.constant.Params;
import com.submerge.model.entity.User;
import com.submerge.security.model.SecurityUser;
import com.submerge.service.UserService;

@Component("userBean")
@Scope(value = "session")
public class UserBean extends AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = 3122997861016843649L;

	@Autowired
	private transient UserService userService;

	@Autowired
	private SecurityUser securityUser;

	private boolean remember;

	@PostConstruct
	public void init() {
		
	}
	// ====================== public methods start =======================

	public String login() {
		this.securityUser.loginSuccess(getRequest(), getResponse(), this.remember);
		return getViewId() + "?" + Params.REDIRECT;
	}

	public String logout() {
		this.securityUser.logout(getRequest(), getResponse());
		return getDefaultRedirect();
	}

	public String register() {
		initRegistration();
		this.userService.create(getUser());
		this.securityUser.loginSuccess(getRequest(), getResponse(), false);
		return getDefaultRedirect();
	}

	// ====================== private methods start ======================

	private void initRegistration() {
		User user = getUser();
		Date currDate = Calendar.getInstance().getTime();
		user.setCreation(currDate);
		user.setLastLogin(currDate);
		user.setPassword(this.userService.hashPassword(getUser().getPassword()));
	}

	// ===================== getter and setter start =====================

	public User getUser() {
		return this.securityUser.getUser();
	}

	public void setUser(User user) {
		this.securityUser.setUser(user);
	}

	public boolean isRemember() {
		return this.remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public boolean isLogged() {
		return getUser().getId() > 0;
	}

}
