package com.github.dnbn.submerge.api.subtitle.ass;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.github.dnbn.submerge.api.subtitle.common.SubtitleTime;

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
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

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

	/**
	 * Convert a string pattern to a Local time
	 * 
	 * @param time
	 * @see ASSTime.PATTERN
	 * @return
	 * @throws DateTimeParseException
	 */
	public static LocalTime fromString(String time) {

		return LocalTime.parse(time.replace(',', '.'), FORMATTER);
	}
}
