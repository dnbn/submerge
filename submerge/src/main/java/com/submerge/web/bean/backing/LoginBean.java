package com.submerge.web.bean.backing;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

import com.submerge.constant.Params;
import com.submerge.security.model.AuthenticatedUser;
import com.submerge.web.bean.AbstractManagedBean;

@Component("loginBean")
@Scope(value = "request")
public class LoginBean extends AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = -6467456643657047935L;

	@Autowired
	private transient AuthenticationManager authenticationManager;

	@Autowired
	private transient PersistentTokenBasedRememberMeServices tokenRememberMe;

	@Autowired
	private AuthenticatedUser loggedInUser;

	private String username;
	private String password;
	private boolean remember;

	public String doLogin() {
		String dest = getViewId() + "?" + Params.REDIRECT;
		try {
			processLogin();
		} catch (AuthenticationException e) {
			dest = getErrorRedirect();
		}

		return dest;
	}

	public String doLogout() {
		processLogout();
		return getDefaultRedirect();
	}

	// ======================= private method start =======================

	private void processLogin() throws AuthenticationException {
		Authentication request = new UsernamePasswordAuthenticationToken(this.username, this.password);
		Authentication result = this.authenticationManager.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result);

		HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(getRequest()) {
			@Override
			public String getParameter(String name) {
				return new Boolean(LoginBean.this.isRemember()).toString();
			}
		};
		this.tokenRememberMe.loginSuccess(wrapper, getResponse(), result);
	}

	private void processLogout() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		this.tokenRememberMe.logout(getRequest(), getResponse(), authentication);
		SecurityContextHolder.getContext().setAuthentication(null);
		this.loggedInUser.setUser(null);
	}

	// ===================== getter and setter start =====================

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return this.remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}
}
