package com.submerge.web.pages.bean.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.sub.object.itf.TimedTextFile;
import com.submerge.web.model.entity.SubtitleProfile;
import com.submerge.web.pages.bean.model.proxy.SubConfig;

/**
 * This class is used to access profiles settings from views
 *
 */
@Component("userSubConfigBean")
@Scope(value = "session")
public class UserSubConfigBean implements Serializable {

	private static final long serialVersionUID = 565855830757610447L;

	@Autowired
	private SubConfig subConfig;

	private TimedTextFile firstSubtitle;
	private TimedTextFile secondSubtitle;

	// ====================== public methods start =======================

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(getFilename())) {
			sb.append("Target filename [").append(getFilename()).append("]");
		}
		if (this.firstSubtitle != null && this.firstSubtitle.getFileName() != null) {
			sb.append(" S1 [").append(this.firstSubtitle.getFileName()).append("]");
		}
		if (this.secondSubtitle != null && this.secondSubtitle.getFileName() != null) {
			sb.append(" S2 [").append(this.secondSubtitle.getFileName()).append("]");
		}
		return sb.toString();
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

	// ===================== getter and setter start =====================

	public TimedTextFile getFirstSubtitle() {
		return this.firstSubtitle;
	}

	public void setFirstSubtitle(TimedTextFile subtitleOne) {
		this.firstSubtitle = subtitleOne;
	}

	public TimedTextFile getSecondSubtitle() {
		return this.secondSubtitle;
	}

	public void setSecondSubtitle(TimedTextFile subtitleTwo) {
		this.secondSubtitle = subtitleTwo;
	}

}
