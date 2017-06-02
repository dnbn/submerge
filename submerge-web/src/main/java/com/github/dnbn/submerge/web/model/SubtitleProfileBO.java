package com.github.dnbn.submerge.web.model;

import java.io.Serializable;
import java.util.Date;

public class SubtitleProfileBO implements Serializable {

	private static final long serialVersionUID = 4029322221891362178L;

	private int id;
	private String primaryColor;
	private String outlineColor;
	private int outlineWidth;
	private String fontName;
	private int fontSize;
	private Date lastUpdate;
	private String language;
	private int alignment;
	private int alignmentOffset;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrimaryColor() {
		return this.primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public String getOutlineColor() {
		return this.outlineColor;
	}

	public void setOutlineColor(String outlineColor) {
		this.outlineColor = outlineColor;
	}

	public int getOutlineWidth() {
		return this.outlineWidth;
	}

	public void setOutlineWidth(int outlineWidth) {
		this.outlineWidth = outlineWidth;
	}

	public String getFontName() {
		return this.fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getAlignment() {
		return this.alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	public int getAlignmentOffset() {
		return this.alignmentOffset;
	}

	public void setAlignmentOffset(int alignmentOffset) {
		this.alignmentOffset = alignmentOffset;
	}

	@Override
	public String toString() {

		int align = this.alignment + this.alignmentOffset;

		return "Profile [c=" + this.primaryColor + "/" + this.outlineColor + ", ow=" + this.outlineWidth + ", "
				+ this.fontName + "(" + this.fontSize + "), a=" + align + "]";
	}
}
