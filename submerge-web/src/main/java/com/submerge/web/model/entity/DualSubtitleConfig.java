package com.submerge.web.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private Date lastUpdate;

	public DualSubtitleConfig() {
		this.profileOne = new SubtitleProfile();
		this.profileTwo = new SubtitleProfile();
	}

	public DualSubtitleConfig(User user, SubtitleProfile profileOne, SubtitleProfile profileTwo, boolean current) {
		this.user = user;
		this.profileOne = profileOne;
		this.profileTwo = profileTwo;
		this.current = current;
	}

	public DualSubtitleConfig(User user, SubtitleProfile profileOne, SubtitleProfile profileTwo, String name,
			boolean current, String filename) {
		this.user = user;
		this.profileOne = profileOne;
		this.profileTwo = profileTwo;
		this.name = name;
		this.current = current;
		this.filename = filename;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_dual_subtitle_config_id")
	@SequenceGenerator(name = "s_dual_subtitle_config_id", sequenceName = "s_dual_subtitle_config_id", allocationSize = 1)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_account", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "fk_id_profile_one", nullable = false)
	public SubtitleProfile getProfileOne() {
		return this.profileOne;
	}

	public void setProfileOne(SubtitleProfile profileOne) {
		this.profileOne = profileOne;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update", nullable = false, length = 13)
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
