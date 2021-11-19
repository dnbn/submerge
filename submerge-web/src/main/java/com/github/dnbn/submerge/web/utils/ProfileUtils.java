package com.github.dnbn.submerge.web.utils;

import org.apache.commons.lang3.StringUtils;

import com.github.dnbn.submerge.api.constant.FontName;
import com.github.dnbn.submerge.api.subtitle.common.TimedTextFile;
import com.github.dnbn.submerge.api.subtitle.config.Font;
import com.github.dnbn.submerge.api.subtitle.config.SimpleSubConfig;
import com.github.dnbn.submerge.web.model.SubtitleProfileBO;

public class ProfileUtils {

	/**
	 * Init a subtitle profile
	 * 
	 * @param profile the subtitle profile
	 */
	public static void initProfiles(SubtitleProfileBO... profiles) {

		for (SubtitleProfileBO profile : profiles) {
			if (StringUtils.isEmpty(profile.getFontName())) {
				profile.setPrimaryColor("#fffff9");
				profile.setOutlineColor("000000");
				profile.setOutlineWidth(2);
				profile.setFontName(FontName.Arial.toString());
				profile.setFontSize(16);
				profile.setAlignment(2);
			}
		}
	}

	/**
	 * Create a SubInput object from a ParsableSubtitle, matching a SubtitleProfile
	 * 
	 * @param subtitle: the parsed subtitle
	 * @param profile: the subtitle profile
	 * @param styleName: the name of the style
	 * @param alignment: the alignment of the subtitle (top, bottom,...)
	 * @return the SubInput object
	 */
	public static SimpleSubConfig createSubConfig(TimedTextFile subtitle, SubtitleProfileBO profile, String styleName) {

		Font font = new Font();
		font.setColor(profile.getPrimaryColor());
		font.setName(profile.getFontName());
		font.setOutlineColor(profile.getOutlineColor());
		font.setOutlineWidth(profile.getOutlineWidth());
		font.setSize(profile.getFontSize());
		SimpleSubConfig si = new SimpleSubConfig(subtitle, font);
		si.setStyleName(styleName);
		si.setAlignment(profile.getAlignment() + profile.getAlignmentOffset());

		return si;
	}
}
