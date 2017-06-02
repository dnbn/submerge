package com.github.dnbn.submerge.web.model;

import java.io.Serializable;
import java.util.Date;

public class DualSubtitleConfigBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private SubtitleProfileBO profileOne;
	private SubtitleProfileBO profileTwo;
	private String name;
	private boolean current;
	private String filename;
	private Date lastUpdate;
	private boolean avoidSwitch = true;
	private boolean clean = true;
	private boolean adjustTimecodes;
	private boolean oneLine;
	
	public DualSubtitleConfigBO() {
		this.profileOne = new SubtitleProfileBO();
		this.profileTwo = new SubtitleProfileBO();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SubtitleProfileBO getProfileOne() {
		return this.profileOne;
	}

	public void setProfileOne(SubtitleProfileBO profileOne) {
		this.profileOne = profileOne;
	}

	public SubtitleProfileBO getProfileTwo() {
		return this.profileTwo;
	}

	public void setProfileTwo(SubtitleProfileBO profileTwo) {
		this.profileTwo = profileTwo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCurrent() {
		return this.current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public boolean isAvoidSwitch() {
		return this.avoidSwitch;
	}

	public void setAvoidSwitch(boolean avoidSwitch) {
		this.avoidSwitch = avoidSwitch;
	}

	public boolean isClean() {
		return this.clean;
	}

	public void setClean(boolean clean) {
		this.clean = clean;
	}

	public boolean isAdjustTimecodes() {
		return this.adjustTimecodes;
	}

	public void setAdjustTimecodes(boolean adjustTimecodes) {
		this.adjustTimecodes = adjustTimecodes;
	}

	public boolean isOneLine() {
		return this.oneLine;
	}

	public void setOneLine(boolean oneLine) {
		this.oneLine = oneLine;
	}

}
