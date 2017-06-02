package com.github.dnbn.submerge.web.pages.bean.model.proxy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.dnbn.submerge.web.model.UserBO;

/**
 * This class is used to manage the logged in user
 * 
 * proxyMode interfaces because it is a session scope used in spring services (Singleton)
 * 
 * @see UserDetailsServiceImpl
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
	private UserBO user;

	@Override
	public UserBO getUser() {
		return this.user;
	}

	@Override
	public void setUser(UserBO user) {
		this.user = user;
	}

}
