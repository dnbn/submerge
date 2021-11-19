package com.github.dnbn.submerge.boot.pages.bean.model.proxy;

import com.github.dnbn.submerge.boot.model.DualSubtitleConfig;
import com.github.dnbn.submerge.boot.model.SubtitleProfile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class SessionSubConfig implements SubConfig {

	@Serial
	private static final long serialVersionUID = -445056501423318099L;

	private List<DualSubtitleConfig> dualSubConfigs = new ArrayList<>();

	private DualSubtitleConfig currentdualSubConfig = new DualSubtitleConfig();

	private SubtitleProfile simpleAssConfig = new SubtitleProfile();

	public SessionSubConfig() {
		this.currentdualSubConfig.setCurrent(true);
		this.dualSubConfigs.add(this.currentdualSubConfig);
	}

	// ===================== getter and setter start =====================

	@Override
	public List<DualSubtitleConfig> getConfigs() {
		return this.dualSubConfigs;
	}

	@Override
	public void setConfigs(List<DualSubtitleConfig> dualSubConfigs, SubtitleProfile simpleAssConfig) {
		this.currentdualSubConfig = dualSubConfigs.stream().filter(c -> c.isCurrent()).findFirst().get();
		this.dualSubConfigs = dualSubConfigs;
		this.simpleAssConfig = simpleAssConfig;
	}

	@Override
	public DualSubtitleConfig getCurrent() {
		return this.currentdualSubConfig;
	}

	@Override
	public SubtitleProfile getSimpleAssConfig() {
		return this.simpleAssConfig;
	}

}
