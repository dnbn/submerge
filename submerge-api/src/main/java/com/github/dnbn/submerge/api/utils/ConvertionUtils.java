package com.github.dnbn.submerge.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.dnbn.submerge.api.subtitle.ass.ASSTime;
import com.github.dnbn.submerge.api.subtitle.ass.Events;
import com.github.dnbn.submerge.api.subtitle.ass.V4Style;
import com.github.dnbn.submerge.api.subtitle.common.TimedLine;
import com.github.dnbn.submerge.api.subtitle.common.TimedObject;
import com.github.dnbn.submerge.api.subtitle.config.Font;
import com.github.dnbn.submerge.api.subtitle.config.SimpleSubConfig;

public class ConvertionUtils {

	private static final String RGX_XML_TAG = "<[^>]+>";
	private static final String RGX_ASS_FORMATTING = "\\{[^\\}]*\\}";
	private static final String SRT_ITALIC_CLOSE = "\\</i\\>";
	private static final String SRT_ITALIC_OPEN = "\\<i\\>";
	private static final String ASS_ITALIC_CLOSE = "\\{\\\\i0\\}";
	private static final String ASS_ITALIC_OPEN = "\\{\\\\i1\\}";

	/**
	 * Create an <code>Events</code> object from a timed line
	 * 
	 * @param line: a timed line
	 * @param style: the style name
	 * @return the corresponding <code>Events</code>
	 */
	public static Events createEvent(TimedLine line, String style) {

		List<String> newLine = new ArrayList<>();

		for (String text : line.getTextLines()) {
			newLine.add(toASSString(text));
		}
		TimedObject timeLine = line.getTime();
		ASSTime time = new ASSTime(timeLine.getStart(), timeLine.getEnd());

		return new Events(style, time, newLine);
	}

	/**
	 * Create a <code>V4Style</code> object from <code>SubInput</code>
	 * 
	 * @param config: the configuration object
	 * @return the corresponding style
	 */
	public static V4Style createV4Style(SimpleSubConfig config) {

		V4Style style = new V4Style(config.getStyleName());
		Font font = config.getFontconfig();
		style.setFontname(font.getName());
		style.setFontsize(font.getSize());
		style.setAlignment(config.getAlignment());
		style.setPrimaryColour(ColorUtils.hexToBGR(font.getColor()));
		style.setOutlineColor(ColorUtils.hexToBGR(font.getOutlineColor()));
		style.setOutline(font.getOutlineWidth());
		style.setMarginV(config.getVerticalMargin());
		return style;
	}

	/**
	 * Format a text line to be srt compliant
	 * 
	 * @param textLine the text line
	 * @return the formatted text line
	 */
	public static String toSRTString(String textLine) {

		String formatted = textLine.replaceAll(ASS_ITALIC_OPEN, SRT_ITALIC_OPEN);
		formatted = formatted.replaceAll(ASS_ITALIC_CLOSE, SRT_ITALIC_CLOSE);
		formatted = formatted.replaceAll(RGX_ASS_FORMATTING, StringUtils.EMPTY);

		return formatted;
	}

	/**
	 * Format a text line to be ass compliant
	 * 
	 * @param textLine the text line
	 * @return
	 */
	public static String toASSString(String textLine) {

		String formatted = textLine.replaceAll(SRT_ITALIC_OPEN, ASS_ITALIC_OPEN);
		formatted = formatted.replaceAll(SRT_ITALIC_CLOSE, ASS_ITALIC_CLOSE);

		return formatted.replaceAll(RGX_XML_TAG, StringUtils.EMPTY);
	}
}
