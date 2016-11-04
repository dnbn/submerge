package com.submerge.web.pages.bean.model.proxy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.submerge.web.model.DualSubtitleConfigBO;
import com.submerge.web.model.SubtitleProfileBO;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class SessionSubConfig implements SubConfig {

	private static final long serialVersionUID = -445056501423318099L;

	private Set<DualSubtitleConfigBO> dualSubConfigs = new HashSet<>();

	private DualSubtitleConfigBO currentdualSubConfig = new DualSubtitleConfigBO();

	private Set<DualSubtitleConfigBO> customsDualSubConfig = new HashSet<>();

	private SubtitleProfileBO simpleAssConfig = new SubtitleProfileBO();

	public SessionSubConfig() {
		this.currentdualSubConfig.setCurrent(true);
		this.dualSubConfigs.add(this.currentdualSubConfig);
	}

	// ===================== getter and setter start =====================

	@Override
	public Set<DualSubtitleConfigBO> getConfigs() {
		return this.dualSubConfigs;
	}

	@Override
	public void setConfigs(Set<DualSubtitleConfigBO> dualSubConfigs, SubtitleProfileBO simpleAssConfig) {
		this.currentdualSubConfig = dualSubConfigs.stream().filter(c -> c.isCurrent()).findFirst().get();
		this.customsDualSubConfig = dualSubConfigs.stream().filter(c -> !c.isCurrent())
				.collect(Collectors.toCollection(HashSet::new));
		this.dualSubConfigs = dualSubConfigs;
		this.simpleAssConfig = simpleAssConfig;
	}

	@Override
	public DualSubtitleConfigBO getCurrent() {
		return this.currentdualSubConfig;
	}

	@Override
	public Set<DualSubtitleConfigBO> getCustoms() {
		return this.customsDualSubConfig;
	}

	@Override
	public SubtitleProfileBO getSimpleAssConfig() {
		return this.simpleAssConfig;
	}

}
