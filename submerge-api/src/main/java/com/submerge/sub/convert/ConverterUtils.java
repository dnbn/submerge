package com.submerge.sub.convert;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.submerge.sub.object.ass.ASSTime;
import com.submerge.sub.object.ass.Events;
import com.submerge.sub.object.ass.V4Style;
import com.submerge.sub.object.common.Line;
import com.submerge.sub.object.common.Time;
import com.submerge.sub.object.common.TimedLine;
import com.submerge.sub.object.common.TimedObject;
import com.submerge.sub.object.config.Font;
import com.submerge.sub.object.config.SubInput;
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
	 * @param lines the lines (ascending sort)
	 * @param time the target start time
	 * @return
	 */
	public static TimedLine closestLineByStart(List<? extends TimedLine> lines, final LocalTime time, int tolerance) {

		// Binary search will find the first "random" match
		int iAnyMatch = Collections.binarySearch(lines, new Line<>(new Time(time, null)), new Comparator<TimedLine>() {

			@Override
			public int compare(TimedLine compare, TimedLine base) {

				LocalTime search = base.getTime().getStart();
				LocalTime start = compare.getTime().getStart();

				if (distance(search, start) < tolerance) {
					return 0;
				}

				return start.compareTo(search);
			}
		});

		if (iAnyMatch < 0) {
			return null;
		}

		// Search for other matches
		Set<TimedLine> matches = new TreeSet<>();
		matches.add(lines.get(iAnyMatch));

		int i = iAnyMatch;
		while (i > 0) {
			TimedLine previous = lines.get(--i);
			if (distance(time, previous.getTime().getStart()) >= tolerance) {
				break;
			}
			matches.add(previous);
		}

		i = iAnyMatch;
		while (i < lines.size()) {
			TimedLine next = lines.get(++i);
			if (distance(time, next.getTime().getStart()) >= tolerance) {
				break;
			}
			matches.add(next);
		}

		// return the closest match
		return matches.stream()
				.sorted((m1, m2) -> distance(m1.getTime().getStart(), time) - distance(m2.getTime().getStart(), time))
				.findFirst().get();
	}

	/**
	 * 
	 * @return the absolute distance beetween 2 times
	 */
	static int distance(LocalTime search, LocalTime start) {

		return (int) Math.abs(ChronoUnit.MILLIS.between(search, start));
	}

	/**
	 * Find the line displayed at <code>targetTime</code>
	 * 
	 * @param lines the lines (ascending sort)
	 * @param time the target time
	 * @return
	 */
	public static TimedLine intersectedLines(List<? extends TimedLine> lines, LocalTime time) {

		int index = Collections.binarySearch(lines, new Line<>(new Time(time, null)), new Comparator<TimedLine>() {

			@Override
			public int compare(TimedLine compare, TimedLine base) {

				LocalTime search = base.getTime().getStart();
				LocalTime start = compare.getTime().getStart();
				LocalTime end = compare.getTime().getEnd();

				if ((start.isBefore(search) || start.equals(search)) && (end.isAfter(search) || start.equals(search))) {
					return 0;
				}

				return start.compareTo(search);
			}
		});

		return index < 0 ? null : lines.get(index);
	}
}
