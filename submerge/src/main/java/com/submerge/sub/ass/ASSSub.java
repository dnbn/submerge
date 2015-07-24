package com.submerge.sub.ass;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ASSSub implements Serializable {

	private static final long serialVersionUID = 8812933867812351549L;
	private static final String FORMAT = "Format: ";
	private static final String EVENTS = "[Events]";
	private static final String V4_STYLES = "[V4+ Styles]";
	private static final String SCRIPT_INFO = "[Script Info]";
	private static final String NEW_LINE = "\n";

	private ScriptInfo scriptInfo = new ScriptInfo();
	private List<V4Style> style = new ArrayList<>();
	private Set<Events> events = new TreeSet<>();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// [Script Info]
		sb.append(SCRIPT_INFO).append(NEW_LINE).append(this.scriptInfo.toString());
		sb.append(NEW_LINE).append(NEW_LINE);

		// [V4 Styles]
		sb.append(V4_STYLES).append(NEW_LINE);
		sb.append(FORMAT).append(V4Style.FORMAT_STRING).append(NEW_LINE);
		this.style.forEach(s -> sb.append(s.toString()).append(NEW_LINE));
		sb.append(NEW_LINE);

		// [Events]
		sb.append(EVENTS).append(NEW_LINE);
		sb.append(FORMAT).append(Events.FORMAT_STRING).append(NEW_LINE);
		this.events.forEach(e -> sb.append(e.toString()).append(NEW_LINE));

		return sb.toString();
	}

	/**
	 * Get the ass file as an input stream
	 * 
	 * @return the file
	 */
	public InputStream toInputStream() {
		return new ByteArrayInputStream(toString().getBytes());
	}

	// ===================== getter and setter start =====================

	public ScriptInfo getScriptInfo() {
		return this.scriptInfo;
	}

	public void setScriptInfo(ScriptInfo scriptInfo) {
		this.scriptInfo = scriptInfo;
	}

	public List<V4Style> getStyle() {
		return this.style;
	}

	public void setStyle(List<V4Style> style) {
		this.style = style;
	}

	public Set<Events> getEvents() {
		return this.events;
	}

	public void setEvents(Set<Events> events) {
		this.events = events;
	}

}
