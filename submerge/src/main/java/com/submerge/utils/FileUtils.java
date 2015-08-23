package com.submerge.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.primefaces.model.UploadedFile;

import com.submerge.constant.AppConstants;

public class FileUtils {

	/**
	 * Convert primefaces UploadedFile to java.io.File
	 * 
	 * @param uploadedFile
	 * @return file
	 * @throws IOException
	 */
	public static File toFile(UploadedFile uploadedFile) throws IOException {
		InputStream is = uploadedFile.getInputstream();

		String filename = FilenameUtils.getName(uploadedFile.getFileName());
		File file = new File(filename);

		try (OutputStream os = new FileOutputStream(file)) {
			IOUtils.copy(is, os);
		}

		return file;
	}

	/**
	 * Detect charset encoding of a file
	 * 
	 * @param file
	 *            : the file to detect encoding from
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
	 * @param file
	 *            : the InputStream to detect encoding from
	 * @return the charset encoding
	 * @throws IOException
	 */
	public static String guessEncoding(InputStream is) throws IOException {
		return guessEncoding(IOUtils.toByteArray(is));
	}

	/**
	 * Detect charset encoding of a byte array
	 * 
	 * @param bytes
	 *            : the byte array to detect encoding from
	 * @return the charset encoding
	 */
	public static String guessEncoding(byte[] bytes) {
		UniversalDetector detector = new UniversalDetector(null);

		detector.handleData(bytes, 0, bytes.length);
		detector.dataEnd();

		String encoding = detector.getDetectedCharset();
		detector.reset();

		if (encoding == null) {
			encoding = AppConstants.UTF_8.toString();
		}

		return encoding;
	}

}
