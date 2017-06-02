package com.github.dnbn.submerge.web.service.impl.converter;

import org.apache.commons.beanutils.PropertyUtils;

import com.github.dnbn.submerge.web.model.SubtitleProfileBO;
import com.github.dnbn.submerge.web.model.entity.SubtitleProfile;

public class SubtitleProfileConverter {

	public static SubtitleProfileBO convert(SubtitleProfile profile) {

		if (profile == null) {
			return null;
		}
		
		SubtitleProfileBO profileBO = new SubtitleProfileBO();
		try {
			PropertyUtils.copyProperties(profileBO, profile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return profileBO;
	}

	public static SubtitleProfile convert(SubtitleProfileBO profileBO) {

		if (profileBO == null) {
			return null;
		}
		
		SubtitleProfile profile = new SubtitleProfile();
		try {
			PropertyUtils.copyProperties(profile, profileBO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return profile;
	}
}
