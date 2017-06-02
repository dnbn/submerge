package com.github.dnbn.submerge.web.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "account", schema = "public", uniqueConstraints = { @UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "name") })
public class User implements Serializable {

	private static final long serialVersionUID = 1570608226201407008L;

	private int id;
	private AccountStatus accountStatus;
	private String name;
	private String password;
	private String email;
	private Date creation;
	private Date lastLogin;
	private Date lastUpdate;
	private Set<DualSubtitleConfig> dualSubtitleConfigs = new HashSet<>(0);
	private Set<UserAuthorities> userAuthorities = new HashSet<>(0);
	private SubtitleProfile simpleAssConfig;

	public User() {
	}

	public User(int id, AccountStatus accountStatus, String name, String password, String email, Date creation,
			Date lastLogin, Date lastUpdate) {
		this.id = id;
		this.accountStatus = accountStatus;
		this.name = name;
		this.password = password;
		this.email = email;
		this.creation = creation;
		this.lastLogin = lastLogin;
		this.lastUpdate = lastUpdate;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_account_id")
	@SequenceGenerator(name = "s_account_id", sequenceName = "s_account_id", allocationSize = 1)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_account_status", nullable = false)
	public AccountStatus getAccountStatus() {
		return this.accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", nullable = false, length = 64)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email", nullable = true, length = 320)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation", nullable = false, length = 13)
	public Date getCreation() {
		return this.creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login", nullable = false, length = 13)
	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update", nullable = false, length = 13)
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "user")
	public Set<UserAuthorities> getUserAuthorities() {
		return this.userAuthorities;
	}

	public void setUserAuthorities(Set<UserAuthorities> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "user")
	public Set<DualSubtitleConfig> getDualSubtitleConfigs() {
		return this.dualSubtitleConfigs;
	}

	public void setDualSubtitleConfigs(Set<DualSubtitleConfig> dualSubtitleConfigs) {
		this.dualSubtitleConfigs = dualSubtitleConfigs;
	}
	
	@ManyToOne(fetch = FetchType.EAGER,  cascade = { CascadeType.ALL })
	@JoinColumn(name = "fk_profile_default", nullable = true)
	public SubtitleProfile getSimpleAssConfig() {
		return this.simpleAssConfig;
	}

	public void setSimpleAssConfig(SubtitleProfile simpleAssConfig) {
		this.simpleAssConfig = simpleAssConfig;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name : ").append(this.name);
		sb.append("\nE-mail : ").append(this.email);
		sb.append("\nCreation : ").append(this.creation);
		sb.append("\nLast login :").append(this.lastLogin);
		return sb.toString();
	}
}
