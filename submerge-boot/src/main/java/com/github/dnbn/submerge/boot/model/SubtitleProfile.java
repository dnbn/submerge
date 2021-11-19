package com.github.dnbn.submerge.boot.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class SubtitleProfile implements Serializable {

	@Serial
	private static final long serialVersionUID = 4029322221891362178L;

	private String primaryColor;
	private String outlineColor;
	private int outlineWidth;
	private String fontName;
	private int fontSize;
	private Instant lastUpdate;
	private String language;
	private int alignment;
	private int alignmentOffset;

	@DynamoDbAttribute(value = "primaryColor")
	public String getPrimaryColor() {
		return this.primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	@DynamoDbAttribute(value = "outlineColor")
	public String getOutlineColor() {
		return this.outlineColor;
	}

	public void setOutlineColor(String outlineColor) {
		this.outlineColor = outlineColor;
	}

	@DynamoDbAttribute(value = "outlineWidth")
	public int getOutlineWidth() {
		return this.outlineWidth;
	}

	public void setOutlineWidth(int outlineWidth) {
		this.outlineWidth = outlineWidth;
	}

	@DynamoDbAttribute(value = "fontName")
	public String getFontName() {
		return this.fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	@DynamoDbAttribute(value = "fontSize")
	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	@DynamoDbAttribute(value = "lastUpdate")
	public Instant getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@DynamoDbAttribute(value = "language")
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@DynamoDbAttribute(value = "alignment")
	public int getAlignment() {
		return this.alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	@DynamoDbAttribute(value = "alignmentOffset")
	public int getAlignmentOffset() {
		return this.alignmentOffset;
	}

	public void setAlignmentOffset(int alignmentOffset) {
		this.alignmentOffset = alignmentOffset;
	}
}
