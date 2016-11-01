package com.submerge.web.service;

import com.submerge.sub.object.config.SubInput;
import com.submerge.web.pages.bean.model.UserSubConfigBean;

/**
 * Service used to trace actions
 */
public interface HistoService {

	/**
	 * Trace the action of merging 2 subtitles
	 * 
	 * @param profileOne
	 * @param profileTwo
	 * @param userSubConfig the user sub configuration
	 */
	void traceMerge(SubInput one, SubInput two, UserSubConfigBean userSubConfig);
}