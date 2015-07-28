package com.submerge.web.bean.model.proxy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.submerge.model.entity.DualSubtitleConfig;

@Component("sbmSubConfig")
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class SbmSubConfig implements SubConfig {

	private static final long serialVersionUID = -445056501423318099L;

	private Set<DualSubtitleConfig> configs = new HashSet<>();

	private DualSubtitleConfig current = new DualSubtitleConfig();

	private Set<DualSubtitleConfig> customs = new HashSet<>();

	public SbmSubConfig() {
		this.current.setCurrent(true);
		this.configs.add(this.current);
	}

	// ===================== getter and setter start =====================

	@Override
	public Set<DualSubtitleConfig> getConfigs() {
		return this.configs;
	}

	@Override
	public void setConfigs(Set<DualSubtitleConfig> configs) {
		this.current = configs.stream().filter(c -> c.isCurrent()).findFirst().get();
		this.customs = configs.stream().filter(c -> !c.isCurrent()).collect(Collectors.toCollection(HashSet::new));
		this.configs = configs;
	}

	@Override
	public DualSubtitleConfig getCurrent() {
		return this.current;
	}

	@Override
	public Set<DualSubtitleConfig> getCustoms() {
		return this.customs;
	}

}
