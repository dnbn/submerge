package submerge.sub.convert;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import submerge.sub.object.ass.ASSSub;
import submerge.sub.object.ass.ASSTime;
import submerge.sub.object.ass.Events;
import submerge.sub.object.ass.V4Style;
import submerge.sub.object.config.Font;
import submerge.sub.object.config.SubInput;
import submerge.sub.object.itf.TimedLine;
import submerge.sub.object.itf.TimedObject;
import submerge.sub.object.itf.TimedTextFile;
import submerge.sub.object.srt.SRTLine;
import submerge.sub.object.srt.SRTSub;
import submerge.sub.object.srt.SRTTime;
import submerge.sub.utils.ColorUtils;

/**
 * Service used to manage subtitles
 */
public class SubtitleConverter {

	private static final String RGX_ASS_FORMATTING = "\\{[^\\}]*\\}";
	private static final String SRT_ITALIC_CLOSE = "\\</i\\>";
	private static final String SRT_ITALIC_OPEN = "\\<i\\>";
	private static final String ASS_ITALIC_CLOSE = "\\{\\\\i0\\}";
	private static final String ASS_ITALIC_OPEN = "\\{\\\\i1\\}";

	/**
	 * TimedTextFile to SRT conversion
	 * 
	 * @param timedFile the TimedTextFile
	 * @return the SRTSub object
	 */
	public SRTSub toSRT(TimedTextFile timedFile) {

		SRTSub srt = new SRTSub();

		int i = 0;
		for (TimedLine timedLine : timedFile.getTimedLines()) {
			int id = ++i;
			TimedObject time = timedLine.getTime();
			LocalTime start = time.getStart();
			LocalTime end = time.getEnd();
			SRTTime srtTime = new SRTTime(start, end);

			List<String> textLines = timedLine.getTextLines();
			List<String> newLines = new ArrayList<>();

			for (String textLine : textLines) {
				newLines.add(toSRTString(textLine));
			}

			SRTLine srtLine = new SRTLine(id, srtTime, newLines);

			srt.add(srtLine);
		}

		return srt;
	}

	/**
	 * SubInput to ASS conversion
	 * 
	 * @param config the configuration object
	 * @return the ASSSub object
	 */
	public ASSSub toASS(SubInput config) {

		return mergeToAss(config);
	}

	/**
	 * Merge several subtitles into one ASS
	 * 
	 * @param configs : configuration object of the subtitles
	 * @return
	 */
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
			newLine.add(toASSString(text));
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

	/**
	 * Format a text line to be srt compliant
	 * 
	 * @param textLine the text line
	 * @return the formatted text line
	 */
	private static String toSRTString(String textLine) {

		String formatted = textLine.replaceAll(ASS_ITALIC_OPEN, SRT_ITALIC_OPEN);
		formatted = formatted.replaceAll(ASS_ITALIC_CLOSE, SRT_ITALIC_CLOSE);
		formatted = formatted.replaceAll(RGX_ASS_FORMATTING, StringUtils.EMPTY);

		return formatted;
	}

	/**
	 * Format a text line to be ass compliant
	 * 
	 * @param textLine the text line
	 * @return
	 */
	private static String toASSString(String textLine) {

		String formatted = textLine.replaceAll(SRT_ITALIC_OPEN, ASS_ITALIC_OPEN);
		formatted = formatted.replaceAll(SRT_ITALIC_CLOSE, ASS_ITALIC_CLOSE);

		return formatted;
	}

}
