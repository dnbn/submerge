package com.submerge.web.pages.bean.backing;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.web.model.entity.User;
import com.submerge.web.pages.bean.AbstractManagedBean;
import com.submerge.web.service.UserService;

@Component("registrationBean")
@Scope(value = "request")
public class RegistrationBean extends AbstractManagedBean {

	private static final transient Logger logger = Logger.getLogger(RegistrationBean.class.getName());
	
	@Autowired
	private transient UserService userService;

	@Autowired
	private LoginBean loginBean;

	/**
	 * The user
	 */
	private User user = new User();

	/**
	 * Register the user and redirect to the main page
	 * 
	 * @return the page to be displayed
	 */
	public String register() {
		
		this.loginBean.setUsername(this.user.getName());
		this.loginBean.setPassword(this.user.getPassword());
		this.loginBean.setRemember(true);

		processRegistration();
		logger.info("user " + this.user.getName() + " registered");

		this.loginBean.doLogin();
		return getDefaultRedirect();
	}

	// ====================== private methods start ======================

	/**
	 * Create the user
	 */
	private void processRegistration() {
		
		Date currDate = Calendar.getInstance().getTime();
		this.user.setCreation(currDate);
		this.user.setLastLogin(currDate);
		this.user.setPassword(this.userService.hashPassword(this.user.getPassword()));
		this.userService.create(this.user);
	}

	// ====================== getter & setter start ======================

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
