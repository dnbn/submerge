package com.submerge.web.utils;

import org.apache.commons.lang.StringUtils;

import com.submerge.sub.api.object.common.TimedTextFile;
import com.submerge.sub.api.object.config.Font;
import com.submerge.sub.api.object.config.SimpleSubConfig;
import com.submerge.sub.constant.FontName;
import com.submerge.web.model.entity.SubtitleProfile;

public class ProfileUtils {

	/**
	 * Init a subtitle profile
	 * 
	 * @param profile the subtitle profile
	 */
	public static void initProfiles(SubtitleProfile... profiles) {

		for (SubtitleProfile profile : profiles) {
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
	public static SimpleSubConfig createSubConfig(TimedTextFile subtitle, SubtitleProfile profile, String styleName) {

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
