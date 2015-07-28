package com.submerge.web.bean.model.proxy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.submerge.model.entity.User;
import com.submerge.security.service.SbmUserDetailsService;

/**
 * This class is used to manage the logged in user
 * 
 * proxyMode interfaces because it is a session scope used in spring services
 * (Singleton)
 * 
 * @see SbmUserDetailsService
 *
 */
@Component("sbmUser")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class SbmUser implements AuthenticatedUser {

	private static final long serialVersionUID = -7570568936084166388L;

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
