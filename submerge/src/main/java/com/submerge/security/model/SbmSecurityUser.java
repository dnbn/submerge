package com.submerge.security.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

import com.submerge.model.entity.User;

/**
 * This class is used to manage the logged in user
 * 
 * proxyMode interfaces because it is a session scope used in spring services (Singleton)
 * @see SbmUserDetailsService
 *
 */
@Component("securityUser")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class SbmSecurityUser implements SecurityUser {

	private static final long serialVersionUID = 2588717972882828547L;

	@Autowired
	private transient RememberMeServices rememberMeServices;

	@Autowired
	private transient PersistentTokenBasedRememberMeServices tokenRememberMe;

	private User user = new User();

	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void loginSuccess(HttpServletRequest request, HttpServletResponse response, boolean isRemember) {
		SbmUserDetails userDetails = new SbmUserDetails(this.user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());

		HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request) {
			@Override
			public String getParameter(String name) {
				return new Boolean(isRemember).toString();
			}
		};
		this.rememberMeServices.loginSuccess(wrapper, response, authentication);
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		this.tokenRememberMe.logout(request, response, authentication);
		SecurityContextHolder.getContext().setAuthentication(null);
		this.user = new User();
	}

}
