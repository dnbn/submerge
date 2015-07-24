package com.submerge.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dual_subtitle_config", schema = "public")
public class DualSubtitleConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private User user;
	private SubtitleProfile profileOne;
	private SubtitleProfile profileTwo;
	private String name;
	private boolean current;
	private String filename;

	public DualSubtitleConfig() {
	}

	public DualSubtitleConfig(int id, User account, SubtitleProfile profileOne, SubtitleProfile profileTwo,
			boolean current) {
		this.id = id;
		this.user = account;
		this.profileOne = profileOne;
		this.profileTwo = profileTwo;
		this.current = current;
	}

	public DualSubtitleConfig(int id, User account, SubtitleProfile profileOne, SubtitleProfile profileTwo,
			String name, boolean current, String filename) {
		this.id = id;
		this.user = account;
		this.profileOne = profileOne;
		this.profileTwo = profileTwo;
		this.name = name;
		this.current = current;
		this.filename = filename;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_account", nullable = false)
	public User getAccount() {
		return this.user;
	}

	public void setAccount(User account) {
		this.user = account;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_profile_one", nullable = false)
	public SubtitleProfile getProfileOne() {
		return this.profileOne;
	}

	public void setProfileOne(SubtitleProfile profileOne) {
		this.profileOne = profileOne;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_profile_two", nullable = false)
	public SubtitleProfile getProfileTwo() {
		return this.profileTwo;
	}

	public void setProfileTwo(SubtitleProfile profileTwo) {
		this.profileTwo = profileTwo;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "current", nullable = false)
	public boolean isCurrent() {
		return this.current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	@Column(name = "filename", length = 70)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
