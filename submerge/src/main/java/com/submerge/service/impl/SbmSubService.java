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
import com.submerge.sub.srt.SRTLine;
import com.submerge.utils.SubUtils;

@Service("subtitleService")
public class SbmSubService implements SubtitleService {

	@Override
	public ASSSub mergeToAss(SubInput configOne, SubInput configTwo) {
		ASSSub ass = new ASSSub();
		ass.getStyle().add(createV4Style(configOne));
		ass.getStyle().add(createV4Style(configTwo));

		Set<Events> ev = ass.getEvents();
		configOne.getSub().getLines().forEach(line -> ev.add(createEvent(line, configOne.getStyleName())));
		configTwo.getSub().getLines().forEach(line -> ev.add(createEvent(line, configTwo.getStyleName())));

		return ass;
	}

	private static Events createEvent(SRTLine line, String style) {
		List<String> newLine = new ArrayList<>();

		for (String text : line.getTextLines()) {
			// Replace SRT italics by ass equivalents
			// SRT italic <i>myString</i> --> ASS italic {\i1}myString{\i0}
			text = text.replaceAll("\\<i\\>", "{\\\\i1}");
			text = text.replaceAll("\\</i\\>", "{\\\\i0}");
			newLine.add(text);
		}

		return new Events(style, new ASSTime(line.getTime().getStart(), line.getTime().getEnd()), newLine);
	}

	private static V4Style createV4Style(SubInput config) {
		V4Style style = new V4Style(config.getStyleName());
		Font font = config.getFontconfig();
		style.setFontname(font.getName());
		style.setFontsize(font.getSize());
		style.setAlignment(config.getAlignment());
		style.setPrimaryColour(SubUtils.hexToBGR(font.getColor()));
		style.setOutlineColor(SubUtils.hexToBGR(font.getOutlineColor()));
		style.setOutline(font.getOutlineWidth());
		return style;
	}

}
