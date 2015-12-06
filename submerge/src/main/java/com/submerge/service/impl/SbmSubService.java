package com.submerge.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.submerge.service.SubtitleService;
import com.submerge.sub.ass.ASSSub;
import com.submerge.sub.ass.ASSTime;
import com.submerge.sub.ass.Events;
import com.submerge.sub.ass.V4Style;
import com.submerge.sub.config.Font;
import com.submerge.sub.config.SubInput;
import com.submerge.sub.itf.TimedLine;
import com.submerge.sub.itf.TimedObject;
import com.submerge.sub.itf.TimedTextFile;
import com.submerge.utils.ColorUtils;

@Service("subtitleService")
public class SbmSubService implements SubtitleService {

	@Override
	public ASSSub mergeToAss(SubInput... configs) {
		ASSSub ass = new ASSSub();
		Set<Events> ev = ass.getEvents();

		for (SubInput config : configs) {
			ass.getStyle().add(createV4Style(config));
			TimedTextFile sub = config.getSub();
			sub.getTimedLines().forEach(line -> ev.add(createEvent(line, config.getStyleName())));
		}

		return ass;
	}

	/**
	 * Create an <code>Events</code> object from a timed line
	 * 
	 * @param line: a timed line
	 * @param style: the style name
	 * @return the corresponding <code>Events</code>
	 */
	private static Events createEvent(TimedLine line, String style) {
		List<String> newLine = new ArrayList<>();

		for (String text : line.getTextLines()) {
			// Replace SRT italics by ass equivalents
			// SRT italic <i>myString</i> --> ASS italic {\i1}myString{\i0}
			text = text.replaceAll("\\<i\\>", "{\\\\i1}");
			text = text.replaceAll("\\</i\\>", "{\\\\i0}");
			newLine.add(text);
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
	private static V4Style createV4Style(SubInput config) {
		V4Style style = new V4Style(config.getStyleName());
		Font font = config.getFontconfig();
		style.setFontname(font.getName());
		style.setFontsize(font.getSize());
		style.setAlignment(config.getAlignment());
		style.setPrimaryColour(ColorUtils.hexToBGR(font.getColor()));
		style.setOutlineColor(ColorUtils.hexToBGR(font.getOutlineColor()));
		style.setOutline(font.getOutlineWidth());
		return style;
	}

}
