package com.github.dnbn.submerge.web.pages.bean.backing;

import java.io.Serializable;
import java.util.logging.Logger;

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

import com.github.dnbn.submerge.web.constant.Params;
import com.github.dnbn.submerge.web.pages.bean.AbstractManagedBean;
import com.github.dnbn.submerge.web.pages.bean.model.proxy.AuthenticatedUser;

@Component("loginBean")
@Scope(value = "request")
public class LoginBean extends AbstractManagedBean implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -6467456643657047935L;

	private static final transient Logger logger = Logger.getLogger(LoginBean.class.getName());
	
	@Autowired
	private transient AuthenticationManager authenticationManager;

	@Autowired
	private transient PersistentTokenBasedRememberMeServices tokenRememberMe;

	@Autowired
	private AuthenticatedUser authenticatedUser;

	/**
	 * Username filled by the user
	 */
	private String username;

	/**
	 * Password filled by the user
	 */
	private String password;

	/**
	 * Remember me chackbox filled by the user
	 */
	private boolean remember;

	/**
	 * Login the user and refresh the current page
	 * 
	 * @return the page to be displayed
	 */
	public String doLogin() {
		
		String dest = getViewId() + "?" + Params.REDIRECT;
		try {
			processLogin();
		} catch (AuthenticationException e) {
			dest = getErrorRedirect();
		}

		return dest;
	}

	/**
	 * Logout the user and redirect to the main page
	 */
	public String doLogout() {
		
		processLogout();
		return getDefaultRedirect();
	}

	// ======================= private method start =======================

	/**
	 * Login the user
	 * 
	 * @throws AuthenticationException
	 */
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
		
		logger.info("user " + this.username + " logged in");
	}

	/**
	 * Logout the user
	 */
	private void processLogout() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		this.tokenRememberMe.logout(getRequest(), getResponse(), authentication);
		getExternalContext().invalidateSession();
		
		logger.info("user " + this.username + " logged out");
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
