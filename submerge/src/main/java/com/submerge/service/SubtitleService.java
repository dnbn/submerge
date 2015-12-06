package com.submerge.service;

import com.submerge.sub.ass.ASSSub;
import com.submerge.sub.config.SubInput;

/**
 * Service used to manage subtitles
 */
public interface SubtitleService {

	/**
	 * Merge several subtitles into one ASS
	 * 
	 * @param configs : configuration object of the subtitles
	 * @return
	 */
	public ASSSub mergeToAss(SubInput... configs);

}
