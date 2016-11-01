package com.submerge.sub.convert;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.submerge.sub.object.ass.ASSSub;
import com.submerge.sub.object.ass.Events;
import com.submerge.sub.object.config.SubInput;
import com.submerge.sub.object.itf.TimedLine;
import com.submerge.sub.object.itf.TimedObject;
import com.submerge.sub.object.itf.TimedTextFile;
import com.submerge.sub.object.srt.SRTLine;
import com.submerge.sub.object.srt.SRTSub;
import com.submerge.sub.object.srt.SRTTime;

/**
 * Service used to manage subtitles
 */
public class SubtitleConverter {

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
				newLines.add(ConverterUtils.toSRTString(textLine));
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
			ass.getStyle().add(ConverterUtils.createV4Style(config));
			TimedTextFile sub = config.getSub();
			sub.getTimedLines().forEach(line -> ev.add(ConverterUtils.createEvent(line, config.getStyleName())));
		}

		return ass;
	}

	/**
	 * Transform all multi-lines subtitles to single-line
	 * 
	 * @param timedFile the TimedTextFile
	 */
	public void mergeTextLines(TimedTextFile timedFile) {

		for (TimedLine timedLine : timedFile.getTimedLines()) {
			List<String> textLines = timedLine.getTextLines();
			if (textLines.size() > 1) {
				textLines.set(0, textLines.stream().collect(Collectors.joining(" ")));
				textLines.subList(1, textLines.size()).clear();
			}
		}
	}

	/**
	 * Synchronise the timecodes of a subtitle from another one
	 * 
	 * @param fileToAdjust the subtitle to modify
	 * @param referenceFile the subtitle to take the timecodes from
	 * @param delay the number of milliseconds allowed to differ
	 */
	public void adjustTimecodes(TimedTextFile fileToAdjust, TimedTextFile referenceFile, int delay) {

		List<? extends TimedLine> timedLines = new ArrayList<>(fileToAdjust.getTimedLines());
		List<? extends TimedLine> referenceLines = new ArrayList<>(referenceFile.getTimedLines());

		for (TimedLine lineToAdjust : timedLines) {

			TimedObject originalTime = lineToAdjust.getTime();
			LocalTime originalStart = originalTime.getStart();

			Optional<? extends TimedLine> referenceLine = ConverterUtils.closestLineByStart(referenceLines,
					originalStart, delay);

			if (referenceLine.isPresent()) {
				LocalTime targetStart = referenceLine.get().getTime().getStart();
				LocalTime targetEnd = referenceLine.get().getTime().getEnd();

				Optional<? extends TimedLine> startIntersect = ConverterUtils.intersectedLines(timedLines, targetStart);
				Optional<? extends TimedLine> endIntersect = ConverterUtils.intersectedLines(timedLines, targetEnd);

				boolean hasStartIntersect = startIntersect.isPresent();
				boolean hasEndIntersect = endIntersect.isPresent();

				LocalTime newStart = hasStartIntersect ? startIntersect.get().getTime().getEnd() : targetStart;
				originalTime.setStart(newStart);

				if (!hasEndIntersect || originalTime.getStart().equals(endIntersect.get().getTime().getStart())) {
					originalTime.setEnd(targetEnd);
				} else {
					originalTime.setEnd(endIntersect.get().getTime().getStart());
				}
			}
		}

		SubtitleConverter.expandLongLines(timedLines, referenceLines, delay * 3);
	}

	/**
	 * Once the times are adjusted, we need to detect lines in the adjusted file that
	 * should be displayed during 2 lines of the reference file
	 * 
	 * @param adjustedLines the adjusted lines
	 * @param referenceLines the reference lines
	 */
	private static void expandLongLines(List<? extends TimedLine> adjustedLines,
			List<? extends TimedLine> referenceLines, int delay) {

		for (int i = 0; i < adjustedLines.size(); i++) {

			TimedObject currentElement = adjustedLines.get(i).getTime();
			Optional<? extends TimedLine> referenceLine = referenceLines.stream()
					.filter(r -> r.getTime().equals(currentElement)).findFirst();

			if (referenceLine.isPresent()) {

				int nextReferenceIndex = referenceLines.indexOf(referenceLine.get()) + 1;

				if (nextReferenceIndex < referenceLines.size() && i + 1 < adjustedLines.size()) {

					TimedObject nextReference = referenceLines.get(nextReferenceIndex).getTime();
					TimedObject nextElement = adjustedLines.get(i + 1).getTime();

					if ((nextReference.getStart().isAfter(currentElement.getEnd()) || nextReference.getStart().equals(
							currentElement.getEnd()))
							&& ChronoUnit.MILLIS.between(currentElement.getEnd(), nextReference.getStart()) < delay
							&& (nextElement.getStart().isAfter(nextReference.getEnd()) || nextElement.getStart()
									.equals(nextReference.getEnd()))) {

						currentElement.setEnd(nextReference.getEnd());
					}
				}
			}
		}
	}
}
