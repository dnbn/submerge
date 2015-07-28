package com.submerge.web.bean.model;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.model.entity.SubtitleProfile;
import com.submerge.sub.srt.SRTSub;
import com.submerge.web.bean.model.proxy.SubConfig;

@Component("userSubConfigBean")
@Scope(value = "session")
public class UserSubConfigBean implements Serializable {

	private static final long serialVersionUID = 565855830757610447L;

	@Autowired
	private SubConfig subConfig;

	private SRTSub topSubtitle;
	private SRTSub bottomSubtitle;

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

	public SRTSub getTopSubtitle() {
		return this.topSubtitle;
	}

	public void setTopSubtitle(SRTSub topSubtitle) {
		this.topSubtitle = topSubtitle;
	}

	public SRTSub getBottomSubtitle() {
		return this.bottomSubtitle;
	}

	public void setBottomSubtitle(SRTSub bottomSubtitle) {
		this.bottomSubtitle = bottomSubtitle;
	}

}
