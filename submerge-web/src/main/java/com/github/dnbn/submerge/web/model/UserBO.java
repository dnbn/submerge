package com.github.dnbn.submerge.web.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserBO implements UserDetails {

	private static final long serialVersionUID = 8109287543247336865L;

	private int id;
	private AccountState accountStatus;
	private String name;
	private String email;
	private Date creation;
	private String password;
	private Date lastLogin;
	private Date lastUpdate;
	private Set<DualSubtitleConfigBO> dualSubtitleConfigs = new HashSet<>(0);
	private SubtitleProfileBO simpleAssConfig;
	private List<AuthorityBO> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.authorities;
	}

	@Override
	public String getPassword() {

		return this.password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	@Override
	public String getUsername() {

		return this.getName();
	}

	@Override
	public boolean isAccountNonExpired() {

		return this.getAccountStatus().getId() != AccountState.DISABLED.getId();
	}

	@Override
	public boolean isAccountNonLocked() {

		return this.getAccountStatus().getId() != AccountState.LOCKED.getId();
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return this.getAccountStatus().getId() == AccountState.ENABLED.getId();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AccountState getAccountStatus() {
		return this.accountStatus;
	}

	public void setAccountStatus(AccountState accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreation() {
		return this.creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public void setAuthorities(List<AuthorityBO> authorities) {
		this.authorities = authorities;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Set<DualSubtitleConfigBO> getDualSubtitleConfigs() {
		return this.dualSubtitleConfigs;
	}

	public void setDualSubtitleConfigs(Set<DualSubtitleConfigBO> dualSubtitleConfigs) {
		this.dualSubtitleConfigs = dualSubtitleConfigs;
	}

	public SubtitleProfileBO getSimpleAssConfig() {
		return this.simpleAssConfig;
	}

	public void setSimpleAssConfig(SubtitleProfileBO simpleAssConfig) {
		this.simpleAssConfig = simpleAssConfig;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
