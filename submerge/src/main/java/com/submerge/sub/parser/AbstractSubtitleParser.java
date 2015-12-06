package com.submerge.sub.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

import com.submerge.exception.InvalidFileException;
import com.submerge.exception.InvalidSubException;
import com.submerge.sub.itf.TimedTextFile;
import com.submerge.sub.parser.itf.SubtitleParser;
import com.submerge.utils.FileUtils;

public abstract class AbstractSubtitleParser implements SubtitleParser {

	/**
	 * UTF-8 BOM Marker
	 */
	private static final char BOM_MARKER = '\ufeff';

	@Override
	public abstract TimedTextFile parse(File file) throws InvalidSubException, InvalidFileException;

	/**
	 * Open the file, define the <code>ParsableSubtitle</code> filename and call the abstract parsing method
	 * 
	 * @param file: the subtitle file
	 * @param sub: the subtitle object
	 * @return the subtitle object
	 * @throws InvalidFileException if the file is invalid
	 * @throws InvalidSubException if an error has occured with the subtitle file
	 */
	protected TimedTextFile openAndParse(File file, TimedTextFile sub) throws InvalidFileException,
			InvalidSubException {

		if (!file.isFile()) {
			throw new InvalidFileException("File " + file.getName() + " is invalid");
		}

		try (FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, FileUtils.guessEncoding(file));
				BufferedReader br = new BufferedReader(isr)) {

			skipBom(br);

			sub.setFileName(file.getName());
			parse(br, sub);

		} catch (IOException e) {
			throw new InvalidFileException(e);
		}

		return sub;

	}

	/**
	 * Parse the subtitle file into a <code>ParsableSubtitle</code> object
	 * 
	 * @param br: the buffered reader
	 * @param parsableSub : the subtitle object to fill
	 * @throws IOException
	 * @throws InvalidSubException if an error has occured when parsing the subtitle file
	 */
	protected abstract void parse(BufferedReader br, TimedTextFile parsableSub) throws IOException,
			InvalidSubException;

	/**
	 * Ignore blank spaces and return the first text line
	 * 
	 * @param br: the buffered reader
	 * @throws IOException
	 */
	protected static String readFirstTextLine(BufferedReader br) throws IOException {
		String line = null;
		while ((line = br.readLine()) != null) {
			if (!StringUtils.isEmpty(line.trim())) {
				break;
			}
		}
		return line;
	}

	/**
	 * Remove the byte order mark if exists
	 * 
	 * @param br: the buffered reader
	 * @throws IOException
	 */
	private static void skipBom(BufferedReader br) throws IOException {
		br.mark(4);
		if (BOM_MARKER != br.read()) {
			br.reset();
		}
	}

}
