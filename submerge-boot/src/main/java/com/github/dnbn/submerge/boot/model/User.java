package com.github.dnbn.submerge.boot.model;

import java.io.Serial;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class User implements UserDetails {

	@Serial
	private static final long serialVersionUID = 8109287543247336865L;

	private String login;
	private String email;
	private Instant creation;
	private String password;
	private Instant lastLogin;
	private Instant lastUpdate;
	private List<DualSubtitleConfig> dualSubtitleConfigs = new ArrayList<>();
	private SubtitleProfile simpleAssConfig;

	@DynamoDbPartitionKey
	@DynamoDbAttribute(value = "login")
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@DynamoDbAttribute(value = "password")
	@Override
	public String getPassword() {

		return this.password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.login;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@DynamoDbAttribute(value = "email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@DynamoDbAttribute(value = "creation")
	public Instant getCreation() {
		return this.creation;
	}

	public void setCreation(Instant creation) {
		this.creation = creation;
	}

	@DynamoDbAttribute(value = "lastLogin")
	public Instant getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	@DynamoDbAttribute(value = "dualSubtitleConfigs")
	public List<DualSubtitleConfig> getDualSubtitleConfigs() {
		return this.dualSubtitleConfigs;
	}

	public void setDualSubtitleConfigs(List<DualSubtitleConfig> dualSubtitleConfigs) {
		this.dualSubtitleConfigs = dualSubtitleConfigs;
	}

	@DynamoDbAttribute(value = "simpleAssConfig")
	public SubtitleProfile getSimpleAssConfig() {
		return this.simpleAssConfig;
	}

	public void setSimpleAssConfig(SubtitleProfile simpleAssConfig) {
		this.simpleAssConfig = simpleAssConfig;
	}

	@DynamoDbAttribute(value = "lastUpdate")
	public Instant getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
