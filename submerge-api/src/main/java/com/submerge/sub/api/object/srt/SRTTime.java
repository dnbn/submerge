package com.submerge.sub.api.object.srt;

import java.time.LocalTime;
import java.time.temporal.ChronoField;

import com.submerge.sub.api.object.common.SubtitleTime;

public class SRTTime extends SubtitleTime {

	private static final long serialVersionUID = -5787808223967579723L;

	public static final String PATTERN = "HH:mm:ss,SSS";
	private static final String TS_PATTERN = "%02d:%02d:%02d,%03d";
	public static final String DELIMITER = " --> ";

	public SRTTime() {
		super();
	}

	public SRTTime(LocalTime start, LocalTime end) {
		
		super(start, end);
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(format(this.start));
		sb.append(DELIMITER);
		sb.append(format(this.end));
		return sb.toString();
	}

	// ===================== private method =====================

	private static String format(LocalTime time) {

		int hr = time.get(ChronoField.HOUR_OF_DAY);
		int min = time.get(ChronoField.MINUTE_OF_HOUR);
		int sec = time.get(ChronoField.SECOND_OF_MINUTE);
		int ms = time.get(ChronoField.MILLI_OF_SECOND);

		return String.format(TS_PATTERN, hr, min, sec, ms);
	}
}
