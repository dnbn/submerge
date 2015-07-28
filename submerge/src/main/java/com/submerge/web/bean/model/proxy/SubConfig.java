package com.submerge.web.bean.model.proxy;

import java.io.Serializable;
import java.util.Set;

import com.submerge.model.entity.DualSubtitleConfig;

public interface SubConfig extends Serializable {

	Set<DualSubtitleConfig> getConfigs();

	void setConfigs(Set<DualSubtitleConfig> configs);

	DualSubtitleConfig getCurrent();

	Set<DualSubtitleConfig> getCustoms();

}