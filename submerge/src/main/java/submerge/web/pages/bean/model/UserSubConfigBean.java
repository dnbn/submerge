package com.submerge.web.bean.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.model.entity.SubtitleProfile;
import com.submerge.sub.itf.TimedTextFile;
import com.submerge.web.bean.model.proxy.SubConfig;

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

	private TimedTextFile topSubtitle;
	private TimedTextFile bottomSubtitle;

	// ====================== public methods start =======================

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(getFilename())) {
			sb.append("Target filename [").append(getFilename()).append("]");
		}
		if (this.topSubtitle != null && this.topSubtitle.getFileName() != null) {
			sb.append(" Top [").append(this.topSubtitle.getFileName()).append("]");
		}
		if (this.bottomSubtitle != null && this.bottomSubtitle.getFileName() != null) {
			sb.append(" Bottom [").append(this.bottomSubtitle.getFileName()).append("]");
		}
		return sb.toString();
	}

	// ========================= delegates start =========================

	public SubtitleProfile getProfileTop() {
		return this.subConfig.getCurrent().getProfileOne();
	}

	public SubtitleProfile getProfileBottom() {
		return this.subConfig.getCurrent().getProfileTwo();
	}

	public String getFilename() {
		return this.subConfig.getCurrent().getFilename();
	}

	public void setFilename(String filename) {
		this.subConfig.getCurrent().setFilename(filename);
	}

	// ===================== getter and setter start =====================

	public TimedTextFile getTopSubtitle() {
		return this.topSubtitle;
	}

	public void setTopSubtitle(TimedTextFile topSubtitle) {
		this.topSubtitle = topSubtitle;
	}

	public TimedTextFile getBottomSubtitle() {
		return this.bottomSubtitle;
	}

	public void setBottomSubtitle(TimedTextFile bottomSubtitle) {
		this.bottomSubtitle = bottomSubtitle;
	}

}
