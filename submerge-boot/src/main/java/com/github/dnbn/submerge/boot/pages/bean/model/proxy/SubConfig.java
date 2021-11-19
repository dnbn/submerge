package com.github.dnbn.submerge.boot.pages.bean.model.proxy;

import java.io.Serializable;
import java.util.List;

import com.github.dnbn.submerge.boot.model.DualSubtitleConfig;
import com.github.dnbn.submerge.boot.model.SubtitleProfile;

public interface SubConfig extends Serializable {

	List<DualSubtitleConfig> getConfigs();

	void setConfigs(List<DualSubtitleConfig> dualSubConfigs, SubtitleProfile simpleAssConfig);

	DualSubtitleConfig getCurrent();

	SubtitleProfile getSimpleAssConfig();

}