package com.github.dnbn.submerge.boot.pages.bean.support;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.dnbn.submerge.api.constant.FontName;

@Component("dropDownBean")
@Scope("application")
public class DropDownBean implements Serializable {

	private static final long serialVersionUID = -730042616379687095L;

	private static final int STEP_FONT_SIZE = 2;
	private static final int MIN_FONT_SIZE = 8;
	private static final int MAX_FONT_SIZE = 30;
	private static final int MIN_OUTLINE_WIDTH = 0;
	private static final int MAX_OUTLINE_WIDTH = 5;

	private static Set<Integer> fontSize;
	private static Set<FontName> fontName;
	private static Set<Integer> outlineWidth;
	private static Set<Double> framerate;

	// ===================== Static initialisation =======================

	static {
		fontSize = new LinkedHashSet<>();
		for (int i = MIN_FONT_SIZE; i < MAX_FONT_SIZE; i += STEP_FONT_SIZE) {
			fontSize.add(new Integer(i));
		}
	}

	static {
		fontName = EnumSet.allOf(FontName.class);
	}

	static {
		outlineWidth = new LinkedHashSet<>();
		for (int i = MIN_OUTLINE_WIDTH; i < MAX_OUTLINE_WIDTH; i++) {
			outlineWidth.add(new Integer(i));
		}
	}

	static {
		framerate = new LinkedHashSet<>();
		framerate.add(23.976);
		framerate.add(24.000);
		framerate.add(25.000);
		framerate.add(29.970);
		framerate.add(30.000);
		framerate.add(60.000);
	}

	// ===================== getter and setter start =====================

	public Set<Integer> getFontSize() {
		return DropDownBean.fontSize;
	}

	public void setFontSize(Set<Integer> fontSize) {
		DropDownBean.fontSize = fontSize;
	}

	public Set<FontName> getFontName() {
		return DropDownBean.fontName;
	}

	public Set<Integer> getOutlineWidth() {
		return DropDownBean.outlineWidth;
	}

	public Set<Double> getFramerate() {
		return DropDownBean.framerate;
	}

}
