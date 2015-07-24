package com.submerge.service;

import com.submerge.sub.ass.ASSSub;
import com.submerge.sub.config.SubInput;

public interface SubtitleService {

	/**
	 * Merge two SRT subtitles into one ASS
	 * 
	 * @param configOne
	 *            : configuration object of the first subtitle
	 * @param configTwo
	 *            : configuration object of the second subtitle
	 * @return
	 */
	ASSSub mergeToAss(SubInput configOne, SubInput configTwo);

}
