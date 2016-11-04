package com.submerge.web.service.impl.converter;

import java.util.HashSet;
import java.util.Set;

import com.submerge.web.model.DualSubtitleConfigBO;
import com.submerge.web.model.entity.DualSubtitleConfig;

public class DualSubtitleConfigConverter {

	public static Set<DualSubtitleConfigBO> convertToBO(Set<DualSubtitleConfig> config) {

		Set<DualSubtitleConfigBO> result = new HashSet<>(0);
		config.forEach(c -> result.add(convert(c)));

		return result;
	}

	public static Set<DualSubtitleConfig> convertBO(Set<DualSubtitleConfigBO> config) {

		Set<DualSubtitleConfig> result = new HashSet<>(0);
		config.forEach(c -> result.add(convert(c)));

		return result;
	}

	public static DualSubtitleConfigBO convert(DualSubtitleConfig config) {

		DualSubtitleConfigBO configBO = new DualSubtitleConfigBO();

		configBO.setAdjustTimecodes(config.isAdjustTimecodes());
		configBO.setAvoidSwitch(config.isAvoidSwitch());
		configBO.setClean(config.isClean());
		configBO.setCurrent(config.isCurrent());
		configBO.setFilename(config.getFilename());
		configBO.setId(config.getId());
		configBO.setLastUpdate(config.getLastUpdate());
		configBO.setName(config.getName());
		configBO.setOneLine(config.isOneLine());
		configBO.setProfileOne(SubtitleProfileConverter.convert(config.getProfileOne()));
		configBO.setProfileTwo(SubtitleProfileConverter.convert(config.getProfileTwo()));

		return configBO;
	}

	public static DualSubtitleConfig convert(DualSubtitleConfigBO configBO) {

		DualSubtitleConfig config = new DualSubtitleConfig();

		config.setAdjustTimecodes(configBO.isAdjustTimecodes());
		config.setAvoidSwitch(configBO.isAvoidSwitch());
		config.setClean(configBO.isClean());
		config.setCurrent(configBO.isCurrent());
		config.setFilename(configBO.getFilename());
		config.setId(configBO.getId());
		config.setLastUpdate(configBO.getLastUpdate());
		config.setName(configBO.getName());
		config.setOneLine(configBO.isOneLine());
		config.setProfileOne(SubtitleProfileConverter.convert(configBO.getProfileOne()));
		config.setProfileTwo(SubtitleProfileConverter.convert(configBO.getProfileTwo()));

		return config;
	}

}
