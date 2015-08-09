package com.submerge.service;

import com.submerge.sub.ass.ASSSub;
import com.submerge.sub.config.SubInput;

public interface SubtitleService {

	/**
	 * Merge several SRT subtitles into one ASS
	 * 
	 * @param configs
	 *            : configuration object of the subtitles
	 * @return
	 */
	ASSSub mergeToAss(SubInput... configs);

}
