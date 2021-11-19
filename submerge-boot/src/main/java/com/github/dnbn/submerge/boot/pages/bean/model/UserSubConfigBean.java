package com.github.dnbn.submerge.boot.pages.bean.model;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.dnbn.submerge.api.subtitle.common.TimedTextFile;
import com.github.dnbn.submerge.boot.model.SubtitleProfile;
import com.github.dnbn.submerge.boot.pages.bean.model.proxy.SubConfig;

/**
 * This class is used to access profiles settings from views
 *
 */
@Component("userSubConfigBean")
@Scope(value = "session")
public class UserSubConfigBean implements Serializable {

	private static final long serialVersionUID = 565855830757610447L;

	private final SubConfig subConfig;

	private TimedTextFile firstSubtitle;
	private TimedTextFile secondSubtitle;

	public UserSubConfigBean(SubConfig subConfig) {
		this.subConfig = subConfig;
	}

	// ========================= delegates start =========================

	public SubtitleProfile getProfileOne() {
		return this.subConfig.getCurrent().getProfileOne();
	}

	public SubtitleProfile getProfileTwo() {
		return this.subConfig.getCurrent().getProfileTwo();
	}

	public SubtitleProfile getProfileSimple() {
		return this.subConfig.getSimpleAssConfig();
	}

	public String getFilename() {
		return this.subConfig.getCurrent().getFilename();
	}

	public void setFilename(String filename) {
		this.subConfig.getCurrent().setFilename(filename);
	}

	public boolean isAvoidSwitch() {
		return this.subConfig.getCurrent().isAvoidSwitch();
	}

	public void setAvoidSwitch(boolean avoidSwitch) {
		this.subConfig.getCurrent().setAvoidSwitch(avoidSwitch);
	}

	public boolean isClean() {
		return this.subConfig.getCurrent().isClean();
	}

	public void setClean(boolean clean) {
		this.subConfig.getCurrent().setClean(clean);
	}

	public boolean isAdjustTimecodes() {
		return this.subConfig.getCurrent().isAdjustTimecodes();
	}

	public void setAdjustTimecodes(boolean adjustTimecodes) {
		this.subConfig.getCurrent().setAdjustTimecodes(adjustTimecodes);
	}

	public boolean isOneLine() {
		return this.subConfig.getCurrent().isOneLine();
	}

	public void setOneLine(boolean oneLine) {
		this.subConfig.getCurrent().setOneLine(oneLine);
	}

	// ===================== getter and setter start =====================

	public TimedTextFile getFirstSubtitle() {
		return SerializationUtils.clone(this.firstSubtitle);
	}

	public void setFirstSubtitle(TimedTextFile subtitleOne) {
		this.firstSubtitle = subtitleOne;
	}

	public TimedTextFile getSecondSubtitle() {
		return SerializationUtils.clone(this.secondSubtitle);
	}

	public void setSecondSubtitle(TimedTextFile subtitleTwo) {
		this.secondSubtitle = subtitleTwo;
	}

}
