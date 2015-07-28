package com.submerge.sub.srt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.submerge.exception.InvalidFileException;
import com.submerge.exception.InvalidSRTSubException;

public final class SRTParser {

	private static final char BOM_MARKER = '\ufeff';

	/**
	 * Parse a SRT formatted file and return the corresponding subtitle object
	 * 
	 * @param srtSubFile
	 * @return
	 * @throws InvalidSRTSubException
	 * @throws InvalidFileException
	 */
	public static SRTSub parse(File file) throws InvalidSRTSubException, InvalidFileException {
		if (!file.isFile()) {
			throw new InvalidFileException("File " + file.getName() + " is invalid");
		}

		SRTSub sub = new SRTSub();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			skipBom(br);
			boolean found = true;
			while (found) {
				SRTLine line = firstIn(br);
				if (found = (line != null)) {
					sub.add(line);
				}
			}
		} catch (IOException e) {
			throw new InvalidFileException(e);
		}
		sub.setFileName(file.getName());

		return sub;
	}

	/**
	 * Extract the firt SRTLine found in a buffered reader
	 * 
	 * @param br
	 * @return SRTLine the line extracted, null if no SRTLine found
	 * @throws IOException
	 * @throws InvalidSRTSubException
	 */
	private static SRTLine firstIn(BufferedReader br) throws IOException, InvalidSRTSubException {
		String idLine = null;
		while ((idLine = br.readLine()) != null) {
			if (!StringUtils.isEmpty(idLine.trim())) {
				break;
			}
		}

		String timeLine = br.readLine();

		if (idLine == null || timeLine == null) {
			return null;
		}

		int id = parseId(idLine);
		SRTTime time = parseTime(timeLine);

		List<String> textLines = new ArrayList<>();
		String testLine;
		while ((testLine = br.readLine()) != null) {
			if (StringUtils.isEmpty(testLine.trim())) {
				break;
			}
			textLines.add(testLine);
		}

		return new SRTLine(id, time, textLines);
	}

	/**
	 * Extract a subtitle id from string
	 * 
	 * @param textLine
	 *            ex 1
	 * @return the id extracted
	 * @throws InvalidSRTSubException
	 */
	private static int parseId(String textLine) throws InvalidSRTSubException {
		int idSRTLine;
		try {
			idSRTLine = Integer.parseInt(textLine.trim());
		} catch (NumberFormatException e) {
			throw new InvalidSRTSubException("Expected id not found -> " + textLine);
		}
		return idSRTLine;
	}

	/**
	 * Extract a subtitle time from string
	 * 
	 * @param timeLine
	 *            ex 00:02:08,822 --> 00:02:11,574
	 * @return the SRTTime object
	 * @throws InvalidSRTSubException
	 */
	private static SRTTime parseTime(String timeLine) throws InvalidSRTSubException {
		SRTTime time = null;
		String times[] = timeLine.split(SRTTime.DELIMITER);
		if (times.length != 2) {
			throw new InvalidSRTSubException("Subtitle " + timeLine + " - invalid times : " + timeLine);
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SRTTime.PATTERN);
			LocalTime start = LocalTime.parse(times[0], formatter);
			LocalTime end = LocalTime.parse(times[1], formatter);
			time = new SRTTime(start, end);
		} catch (DateTimeParseException e) {
			throw new InvalidSRTSubException("Invalid time string : " + timeLine, e);
		}
		return time;
	}

	/**
	 * Remove the byte order mark if exists
	 * 
	 * @param br
	 * @throws IOException
	 */
	private static void skipBom(BufferedReader br) throws IOException {
		br.mark(4);
		if (BOM_MARKER != br.read()) {
			br.reset();
		}
	}

}
