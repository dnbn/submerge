package com.github.dnbn.submerge.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.mozilla.universalchardet.UniversalDetector;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class FileUtils {

	/**
	 * Detect charset encoding of a file
	 * 
	 * @param file: the file to detect encoding from
	 * @return the charset encoding
	 * @throws IOException
	 */
	public static String guessEncoding(File file) throws IOException {
		try (FileInputStream is = new FileInputStream(file)) {
			return guessEncoding(is);
		}
	}

	/**
	 * Detect charset encoding of an input stream
	 * 
	 * @param file: the InputStream to detect encoding from
	 * @return the charset encoding
	 * @throws IOException
	 */
	public static String guessEncoding(InputStream is) throws IOException {
		return guessEncoding(IOUtils.toByteArray(is));
	}

	/**
	 * Detect charset encoding of a byte array
	 * 
	 * @param bytes: the byte array to detect encoding from
	 * @return the charset encoding
	 */
	public static String guessEncoding(byte[] bytes) {
		UniversalDetector detector = new UniversalDetector(null);

		detector.handleData(bytes, 0, bytes.length);
		detector.dataEnd();

		String encoding = detector.getDetectedCharset();
		detector.reset();

		if (encoding == null || "MACCYRILLIC".equals(encoding)) {
			// juniversalchardet incorrectly detects windows-1256 as MACCYRILLIC
			// If encoding is MACCYRILLIC or null, we use ICU4J
			CharsetMatch detected = new CharsetDetector().setText(bytes).detect();
			if (detected != null) {
				encoding = detected.getName();
			} else {
				encoding = "UTF-8";
			}
		}

		return encoding;
	}

}
