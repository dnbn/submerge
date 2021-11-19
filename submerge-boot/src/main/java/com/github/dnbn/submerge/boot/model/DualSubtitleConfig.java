package com.github.dnbn.submerge.boot.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class DualSubtitleConfig implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private SubtitleProfile profileOne;
	private SubtitleProfile profileTwo;
	private String name;
	private boolean current;
	private String filename;
	private Instant lastUpdate;
	private boolean avoidSwitch = true;
	private boolean clean = true;
	private boolean adjustTimecodes;
	private boolean oneLine;
	
	public DualSubtitleConfig() {
		this.profileOne = new SubtitleProfile();
		this.profileTwo = new SubtitleProfile();
	}
	
	@DynamoDbAttribute(value = "profileOne")
	public SubtitleProfile getProfileOne() {
		return this.profileOne;
	}

	public void setProfileOne(SubtitleProfile profileOne) {
		this.profileOne = profileOne;
	}

	@DynamoDbAttribute(value = "profileTwo")
	public SubtitleProfile getProfileTwo() {
		return this.profileTwo;
	}

	public void setProfileTwo(SubtitleProfile profileTwo) {
		this.profileTwo = profileTwo;
	}

	@DynamoDbAttribute(value = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DynamoDbAttribute(value = "current")
	public boolean isCurrent() {
		return this.current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	@DynamoDbAttribute(value = "filename")
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@DynamoDbAttribute(value = "lastUpdate")
	public Instant getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@DynamoDbAttribute(value = "avoidSwitch")
	public boolean isAvoidSwitch() {
		return this.avoidSwitch;
	}

	public void setAvoidSwitch(boolean avoidSwitch) {
		this.avoidSwitch = avoidSwitch;
	}

	@DynamoDbAttribute(value = "clean")
	public boolean isClean() {
		return this.clean;
	}

	public void setClean(boolean clean) {
		this.clean = clean;
	}

	@DynamoDbAttribute(value = "adjustTimecodes")
	public boolean isAdjustTimecodes() {
		return this.adjustTimecodes;
	}

	public void setAdjustTimecodes(boolean adjustTimecodes) {
		this.adjustTimecodes = adjustTimecodes;
	}

	@DynamoDbAttribute(value = "oneLine")
	public boolean isOneLine() {
		return this.oneLine;
	}

	public void setOneLine(boolean oneLine) {
		this.oneLine = oneLine;
	}

}
