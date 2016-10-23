package com.submerge.web.service;

import com.submerge.sub.object.config.SubInput;

/**
 * Service used to trace actions
 */
public interface HistoService {

	/**
	 * Trace the action of merging 2 subtitles
	 * 
	 * @param profileOne
	 * @param profileTwo
	 */
	void traceMerge(SubInput profileOne, SubInput profileTwo);
}