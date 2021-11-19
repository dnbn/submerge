package com.github.dnbn.submerge.boot.pages.bean.model.proxy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.dnbn.submerge.boot.model.User;

/**
 * This class is used to manage the logged in user
 * 
 * proxyMode interfaces because it is a session scope used in spring services
 * (Singleton)
 * 
 *
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class SessionUser implements AuthenticatedUser {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -7570568936084166388L;

	/**
	 * User
	 */
	private User user;

	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

}
