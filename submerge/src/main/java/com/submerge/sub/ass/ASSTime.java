package com.submerge.sub.ass;

import java.io.Serializable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.submerge.sub.itf.TimedObject;

/**
 * The class <code>ASSTime</code> represents a SubStation Alpha time : meaning the time at
 * which the text will appear and disappear onscreen
 *
 */
public class ASSTime implements Serializable, TimedObject {

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
	 * Start Time of the Event, in 0:00:00:00 format ie. Hrs:Mins:Secs:hundredths. This is
	 * the time elapsed during script playback at which the text will appear onscreen.
	 * Note that there is a single digit for the hours!
	 */
	private LocalTime start;

	/**
	 * End Time of the Event, in 0:00:00:00 format ie. Hrs:Mins:Secs:hundredths. This is
	 * the time elapsed during script playback at which the text will disappear offscreen.
	 * Note that there is a single digit for the hours!
	 */
	private LocalTime end;

	/**
	 * Constructor
	 */
	public ASSTime(LocalTime start, LocalTime end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Constructor
	 */
	public ASSTime() {
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

	// ===================== getter and setter start =====================

	@Override
	public LocalTime getStart() {
		return this.start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	@Override
	public LocalTime getEnd() {
		return this.end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

}
