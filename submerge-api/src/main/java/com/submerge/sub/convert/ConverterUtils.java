package com.submerge.sub.convert;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import com.submerge.sub.object.ass.ASSTime;
import com.submerge.sub.object.ass.Events;
import com.submerge.sub.object.ass.V4Style;
import com.submerge.sub.object.config.Font;
import com.submerge.sub.object.config.SubInput;
import com.submerge.sub.object.itf.TimedLine;
import com.submerge.sub.object.itf.TimedObject;
import com.submerge.sub.utils.ColorUtils;

public class ConverterUtils {

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
	public static V4Style createV4Style(SubInput config) {

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

		return formatted;
	}

	/**
	 * Search the line that has the closest start time compared to a specified time. If
	 * the gap beetween the two start times is greater than the toleranceDelay (in ms) the
	 * line will be ignored.
	 * 
	 * @param tolerance the maximum gap in millis
	 * @param lines the lines
	 * @param time the target start time
	 * @return
	 */
	public static Optional<? extends TimedLine> closestLineByStart(Collection<? extends TimedLine> lines,
			LocalTime time, int tolerance) {

		return lines.stream().filter(r -> ChronoUnit.MILLIS.between(time, r.getTime().getStart()) < tolerance)
				.sorted((l1, l2) -> l2.getTime().getStart().compareTo((l1.getTime().getStart()))).findFirst();
	}

	/**
	 * Find the first line displayed at <code>targetTime</code>
	 * 
	 * @param lines the lines
	 * @param time the target time
	 * @return
	 */
	public static Optional<? extends TimedLine> intersectedLines(Collection<? extends TimedLine> lines, LocalTime time) {

		return lines
				.stream()
				.filter(l -> (l.getTime().getStart().isBefore(time) || l.getTime().getStart().equals(time))
						&& (l.getTime().getEnd().isAfter(time) || l.getTime().getStart().equals(time))).findFirst();
	}
}
