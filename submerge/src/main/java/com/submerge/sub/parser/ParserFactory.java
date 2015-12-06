package com.submerge.sub.parser;

import com.submerge.constant.AppConstants;
import com.submerge.sub.parser.itf.SubtitleParser;

public final class ParserFactory {

	/**
	 * Return the subtitle parser for the subtitle format matching the extension
	 * 
	 * @param extension the subtitle extention
	 * @return the subtitle parser, null if no matching parser
	 */
	public static SubtitleParser getParser(String extension) {
		SubtitleParser parser = null;
		String lowerExt = extension.toLowerCase();
		if (AppConstants.ASS.toString().equals(lowerExt) || AppConstants.SSA.toString().equals(lowerExt)) {
			parser = new ASSParser();
		} else if (AppConstants.SRT.toString().equalsIgnoreCase(lowerExt)) {
			parser = new SRTParser();
		}
		return parser;
	}

	/**
	 * Private constructor
	 */
	private ParserFactory() {
		throw new AssertionError();
	}

}
