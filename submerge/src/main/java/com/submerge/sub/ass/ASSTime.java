package com.submerge.sub.ass;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ASSTime implements Serializable {

	
	private static final long serialVersionUID = -8393452818120120069L;
	
	public static final String TIME_PATTERN = "H:mm:ss.SS";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

	/**
	 * Start Time of the Event, in 0:00:00:00 format ie.
	 * Hrs:Mins:Secs:hundredths. This is the time elapsed during script playback
	 * at which the text will appear onscreen. Note that there is a single digit
	 * for the hours!
	 */
	private LocalTime start;

	/**
	 * End Time of the Event, in 0:00:00:00 format ie. Hrs:Mins:Secs:hundredths.
	 * This is the time elapsed during script playback at which the text will
	 * disappear offscreen. Note that there is a single digit for the hours!
	 */
	private LocalTime end;

	public ASSTime(LocalTime start, LocalTime end) {
		this.start = start;
		this.end = end;
	}

	public static String format(LocalTime time) {
		return time.format(FORMATTER);
	}

	// ===================== getter and setter start =====================

	public LocalTime getStart() {
		return this.start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return this.end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

}
