package com.submerge.web.bean.model.proxy;

import java.util.Date;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.submerge.model.entity.AccountStatus;
import com.submerge.model.entity.DualSubtitleConfig;
import com.submerge.model.entity.User;
import com.submerge.model.entity.UserAuthorities;
import com.submerge.security.model.AuthenticatedUser;
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

	// ====================== delegate methods start ======================

	public int getId() {
		return this.user.getId();
	}

	public AccountStatus getAccountStatus() {
		return this.user.getAccountStatus();
	}

	public String getName() {
		return this.user.getName();
	}

	public String getPassword() {
		return this.user.getPassword();
	}

	public String getEmail() {
		return this.user.getEmail();
	}

	public Date getCreation() {
		return this.user.getCreation();
	}

	public Date getLastLogin() {
		return this.user.getLastLogin();
	}

	public Date getLastUpdate() {
		return this.user.getLastUpdate();
	}

	public Set<UserAuthorities> getUserAuthorities() {
		return this.user.getUserAuthorities();
	}

	public Set<DualSubtitleConfig> getDualSubtitleConfigs() {
		return this.user.getDualSubtitleConfigs();
	}

}
