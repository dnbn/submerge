package com.github.dnbn.submerge.boot.service;

import com.github.dnbn.submerge.api.subtitle.config.SimpleSubConfig;
import com.github.dnbn.submerge.boot.pages.bean.model.UserSubConfigBean;

/**
 * Service used to trace actions
 */
public interface HistoService {

	/**
	 * Trace the action of merging 2 subtitles
	 * 
	 * @param one
	 * @param two
	 * @param userSubConfig the user sub configuration
	 */
	void trace(SimpleSubConfig one, SimpleSubConfig two, UserSubConfigBean userSubConfig);
}