package com.submerge.sub.srt;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class SRTTime implements Serializable {

	private static final long serialVersionUID = -5787808223967579723L;
	public static final String PATTERN = "HH:mm:ss,SSS";
	public static final String DELIMITER = " --> ";

	private LocalTime start;
	private LocalTime end;

	public SRTTime(LocalTime start, LocalTime end) {
		this.start = start;
		this.end = end;
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
		StringBuilder sb = new StringBuilder();

		int hours = time.get(ChronoField.HOUR_OF_DAY);
		int minutes = time.get(ChronoField.MINUTE_OF_HOUR);
		int seconds = time.get(ChronoField.SECOND_OF_MINUTE);
		int milliseconds = time.get(ChronoField.MILLI_OF_SECOND);

		if (hours < 10) {
			sb.append("0");
		}
		sb.append(hours).append(":");

		if (minutes < 10) {
			sb.append("0");
		}
		sb.append(minutes).append(":");

		if (seconds < 10) {
			sb.append("0");
		}
		sb.append(seconds).append(",");

		if (seconds < 10) {
			sb.append("00");
		} else if (seconds < 100) {
			sb.append("0");
		}
		sb.append(milliseconds);

		return sb.toString();
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
