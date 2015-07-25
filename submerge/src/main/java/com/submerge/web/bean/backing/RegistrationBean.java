package com.submerge.web.bean.backing;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.model.entity.User;
import com.submerge.service.UserService;
import com.submerge.web.bean.AbstractManagedBean;

@Component("registrationBean")
@Scope(value = "request")
public class RegistrationBean extends AbstractManagedBean {

	@Autowired
	private transient UserService userService;

	@Autowired
	private LoginBean loginBean;

	private User user = new User();

	public String register() {
		this.loginBean.setUsername(this.user.getName());
		this.loginBean.setPassword(this.user.getPassword());

		processRegistration();

		this.loginBean.doLogin();
		return getDefaultRedirect();
	}

	// ====================== private methods start ======================

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
