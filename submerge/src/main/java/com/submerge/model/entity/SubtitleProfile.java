package com.submerge.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

	public SubtitleProfile() {
	}

	public SubtitleProfile(int id, String primaryColor, String outlineColor, int outlineWidth, String fontName,
			int fontSize) {
		this.id = id;
		this.primaryColor = primaryColor;
		this.outlineColor = outlineColor;
		this.outlineWidth = outlineWidth;
		this.fontName = fontName;
		this.fontSize = fontSize;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
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

}
