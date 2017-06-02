package com.github.dnbn.submerge.web.pages.bean.model.proxy;

import java.io.Serializable;
import java.util.Set;

import com.github.dnbn.submerge.web.model.DualSubtitleConfigBO;
import com.github.dnbn.submerge.web.model.SubtitleProfileBO;

public interface SubConfig extends Serializable {

	public Set<DualSubtitleConfigBO> getConfigs();

	public void setConfigs(Set<DualSubtitleConfigBO> dualSubConfigs, SubtitleProfileBO simpleAssConfig);

	public DualSubtitleConfigBO getCurrent();

	public Set<DualSubtitleConfigBO> getCustoms();

	public SubtitleProfileBO getSimpleAssConfig();

}