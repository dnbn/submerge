package com.github.dnbn.submerge.boot.pages.bean.backing;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.dnbn.submerge.boot.model.User;
import com.github.dnbn.submerge.boot.pages.bean.AbstractManagedBean;
import com.github.dnbn.submerge.boot.service.UserService;

@Component("registrationBean")
@Scope(value = "request")
public class RegistrationBean extends AbstractManagedBean {

	private static final transient Logger logger = Logger.getLogger(RegistrationBean.class.getName());

	@Autowired
	private PasswordEncoder passwordEncoder;
	
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

		this.loginBean.setUsername(this.user.getLogin());
		this.loginBean.setPassword(this.user.getPassword());
		this.loginBean.setRemember(true);

		processRegistration();
		logger.info("user " + this.user.getLogin() + " registered");

		this.loginBean.doLogin();
		return getDefaultRedirect();
	}

	// ====================== private methods start ======================

	/**
	 * Create the user
	 */
	private void processRegistration() {

		Instant currDate = ZonedDateTime.now(ZoneId.of("Europe/Paris")).toInstant();
		this.user.setCreation(currDate);
		this.user.setLastLogin(currDate);
		this.user.setPassword(this.passwordEncoder.encode(this.user.getPassword()));
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
