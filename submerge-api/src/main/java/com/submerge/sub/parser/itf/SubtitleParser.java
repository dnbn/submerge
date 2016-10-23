package com.submerge.sub.parser.itf;

import java.io.File;
import java.io.InputStream;

import com.submerge.sub.object.itf.TimedTextFile;
import com.submerge.sub.parser.exception.InvalidFileException;
import com.submerge.sub.parser.exception.InvalidSubException;

public interface SubtitleParser {

	/**
	 * Parse a subtitle file and return the corresponding subtitle object
	 * 
	 * @param file the subtitle file
	 * @return the subtitle object
	 * @throws InvalidSubException if the subtitle is not valid
	 * @throws InvalidFileException if the file is not valid
	 */
	TimedTextFile parse(File file) throws InvalidSubException, InvalidFileException;

	/**
	 * Parse a subtitle file from an inputstream and return the corresponding subtitle
	 * object
	 * 
	 * @param is the input stream
	 * @param fileName the fileName
	 * @return the subtitle object
	 * @throws InvalidSubException if the subtitle is not valid
	 * @throws InvalidFileException if the file is not valid
	 */
	TimedTextFile parse(InputStream is, String fileName) throws InvalidSubException, InvalidFileException;
}
