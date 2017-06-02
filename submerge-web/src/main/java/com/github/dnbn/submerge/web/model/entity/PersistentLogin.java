package com.github.dnbn.submerge.web.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "persistent_logins", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "token"))
public class PersistentLogin implements Serializable {

	private static final long serialVersionUID = 2620410829429044895L;
	private String series;
	private User user;
	private String token;
	private Date lastUsed;

	public PersistentLogin() {
	}

	public PersistentLogin(String series, User user, String token, Date lastUsed) {
		this.series = series;
		this.user = user;
		this.token = token;
		this.lastUsed = lastUsed;
	}

	@Id
	@Column(name = "series", unique = true, nullable = false, length = 64)
	public String getSeries() {
		return this.series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_account", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "token", unique = true, nullable = false, length = 64)
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_used", nullable = false, length = 29)
	public Date getLastUsed() {
		return this.lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Serie : ").append(this.series);
		sb.append("\ntoken : ").append(this.token);
		sb.append("\nLast Used : ").append(this.lastUsed);
		return sb.toString();
	}
}
