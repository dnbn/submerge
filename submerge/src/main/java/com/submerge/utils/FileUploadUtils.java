package com.submerge.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

public class FileUploadUtils {

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

}
