package submerge.web.pages.bean.model.proxy;

import java.io.Serializable;
import java.util.Set;

import submerge.web.model.entity.DualSubtitleConfig;
import submerge.web.model.entity.SubtitleProfile;

public interface SubConfig extends Serializable {

	public Set<DualSubtitleConfig> getConfigs();

	public void setConfigs(Set<DualSubtitleConfig> dualSubConfigs, SubtitleProfile simpleAssConfig);

	public DualSubtitleConfig getCurrent();

	public Set<DualSubtitleConfig> getCustoms();

	public SubtitleProfile getSimpleAssConfig();

}