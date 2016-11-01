package com.submerge.sub.object.srt;

import java.util.List;

import com.submerge.sub.object.itf.TimedLine;
import com.submerge.sub.object.itf.TimedObject;

/**
 * Class <SRTLine> represents an abstract line of SRT, meaning text, timecodes and index
 *
 */
public class SRTLine implements Comparable<SRTLine>, TimedLine {

	private static final long serialVersionUID = -1220593401999895814L;

	private static final String NEW_LINE = "\n";

	private int id;
	private SRTTime time;
	private List<String> textLines;

	public SRTLine(int id, SRTTime time, List<String> textLines) {
		this.id = id;
		this.time = time;
		this.textLines = textLines;
	}

	@Override
	public int compareTo(SRTLine srtLine) {
		return Integer.compare(this.id, srtLine.getId());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.id).append(NEW_LINE);
		sb.append(this.time).append(NEW_LINE);
		this.textLines.forEach(textLine -> sb.append(textLine).append(NEW_LINE));
		return sb.append(NEW_LINE).toString();
	}

	// ===================== getter and setter start =====================

	@Override
	public List<String> getTextLines() {
		return this.textLines;
	}

	@Override
	public TimedObject getTime() {
		return this.time;
	}

	public void setTime(SRTTime time) {
		this.time = time;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTextLines(List<String> textLines) {
		this.textLines = textLines;
	}

}
