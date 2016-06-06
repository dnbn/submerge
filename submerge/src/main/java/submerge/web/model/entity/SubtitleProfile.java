package com.submerge.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "subtitle_profile", schema = "public")
public class SubtitleProfile implements Serializable {

	private static final long serialVersionUID = -2629890137514177186L;

	private int id;
	private String primaryColor;
	private String outlineColor;
	private int outlineWidth;
	private String fontName;
	private int fontSize;
	private Date lastUpdate;
	private String language;

	public SubtitleProfile() {
		super();
	}

	public SubtitleProfile(String primaryColor, String outlineColor, int outlineWidth, String fontName, int fontSize) {
		this.primaryColor = primaryColor;
		this.outlineColor = outlineColor;
		this.outlineWidth = outlineWidth;
		this.fontName = fontName;
		this.fontSize = fontSize;
	}

	public SubtitleProfile(String primaryColor, String outlineColor, int outlineWidth, String fontName, int fontSize,
			Date lastUpdate) {
		this.primaryColor = primaryColor;
		this.outlineColor = outlineColor;
		this.outlineWidth = outlineWidth;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.lastUpdate = lastUpdate;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_subtitle_profile_id")
	@SequenceGenerator(name = "s_subtitle_profile_id", sequenceName = "s_subtitle_profile_id", allocationSize = 1)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "primary_color", nullable = false, length = 7)
	public String getPrimaryColor() {
		return this.primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	@Column(name = "outline_color", nullable = false, length = 7)
	public String getOutlineColor() {
		return this.outlineColor;
	}

	public void setOutlineColor(String outlineColor) {
		this.outlineColor = outlineColor;
	}

	@Column(name = "outline_width", nullable = false)
	public int getOutlineWidth() {
		return this.outlineWidth;
	}

	public void setOutlineWidth(int outlineWidth) {
		this.outlineWidth = outlineWidth;
	}

	@Column(name = "font_name", nullable = false, length = 20)
	public String getFontName() {
		return this.fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	@Column(name = "font_size", nullable = false)
	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update", nullable = false, length = 13)
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Column(name = "language", nullable = true, length = 5)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
