package com.submerge.web.pages.bean.model.proxy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.submerge.web.model.entity.DualSubtitleConfig;
import com.submerge.web.model.entity.SubtitleProfile;

@Component("sbmSubConfig")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class SbmSubConfig implements SubConfig {

	private static final long serialVersionUID = -445056501423318099L;

	private Set<DualSubtitleConfig> dualSubConfigs = new HashSet<>();

	private DualSubtitleConfig currentdualSubConfig = new DualSubtitleConfig();

	private Set<DualSubtitleConfig> customsDualSubConfig = new HashSet<>();

	private SubtitleProfile simpleAssConfig = new SubtitleProfile();

	public SbmSubConfig() {
		this.currentdualSubConfig.setCurrent(true);
		this.dualSubConfigs.add(this.currentdualSubConfig);
	}

	// ===================== getter and setter start =====================

	@Override
	public Set<DualSubtitleConfig> getConfigs() {
		return this.dualSubConfigs;
	}

	@Override
	public void setConfigs(Set<DualSubtitleConfig> dualSubConfigs, SubtitleProfile simpleAssConfig) {
		this.currentdualSubConfig = dualSubConfigs.stream().filter(c -> c.isCurrent()).findFirst().get();
		this.customsDualSubConfig = dualSubConfigs.stream().filter(c -> !c.isCurrent())
				.collect(Collectors.toCollection(HashSet::new));
		this.dualSubConfigs = dualSubConfigs;
		this.simpleAssConfig = simpleAssConfig;
	}

	@Override
	public DualSubtitleConfig getCurrent() {
		return this.currentdualSubConfig;
	}

	@Override
	public Set<DualSubtitleConfig> getCustoms() {
		return this.customsDualSubConfig;
	}

	@Override
	public SubtitleProfile getSimpleAssConfig() {
		return this.simpleAssConfig;
	}

}
