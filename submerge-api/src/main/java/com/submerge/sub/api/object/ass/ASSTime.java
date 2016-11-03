package com.submerge.sub.api.object.ass;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.submerge.sub.api.object.common.SubtitleTime;

/**
 * The class <code>ASSTime</code> represents a SubStation Alpha time : meaning the time at
 * which the text will appear and disappear onscreen
 *
 */
public class ASSTime extends SubtitleTime {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -8393452818120120069L;

	/**
	 * The time pattern
	 */
	public static final String TIME_PATTERN = "H:mm:ss.SS";

	/**
	 * The time pattern formatter
	 */
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

	/**
	 * Constructor
	 */
	public ASSTime(LocalTime start, LocalTime end) {
		super(start, end);
	}

	/**
	 * Constructor
	 */
	public ASSTime() {
		super();
	}

	/**
	 * Convert a <code>LocalTime</code> to string
	 * 
	 * @param time: the time to format
	 * @return the formatted time
	 */
	public static String format(LocalTime time) {
		return time.format(FORMATTER);
	}
}
