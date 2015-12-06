package com.submerge.web.bean.model.proxy;

import java.io.Serializable;
import java.util.Set;

import com.submerge.model.entity.DualSubtitleConfig;

public interface SubConfig extends Serializable {

	public Set<DualSubtitleConfig> getConfigs();

	public void setConfigs(Set<DualSubtitleConfig> configs);

	public DualSubtitleConfig getCurrent();

	public Set<DualSubtitleConfig> getCustoms();

}