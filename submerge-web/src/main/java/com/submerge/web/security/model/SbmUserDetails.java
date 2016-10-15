package com.submerge.web.security.model;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.submerge.web.model.entity.AccountStatus;
import com.submerge.web.model.entity.User;
import com.submerge.web.model.entity.UserAuthorities;

public class SbmUserDetails implements UserDetails {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 8109287543247336865L;

	/**
	 * User
	 */
	private User user;

	/**
	 * Constructor
	 * 
	 * @param user: the user object
	 */
	public SbmUserDetails(User user) {
		super();
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<UserAuthorities> ua = this.user.getUserAuthorities();
		return ua.stream().map(uas -> new SbmAuthority(uas.getAuthorities().getName())).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.user.getAccountStatus().getId() != AccountStatus.Status.DISABLED.getId();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.getAccountStatus().getId() != AccountStatus.Status.LOCKED.getId();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.user.getAccountStatus().getId() == AccountStatus.Status.ENABLED.getId();
	}

}
