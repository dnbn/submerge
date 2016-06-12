package submerge.web.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

public class UploadedFileUtils {

	/**
	 * Convert primefaces UploadedFile to java.io.File
	 * 
	 * @param uploadedFile: the <code>UploadedFile</code> object to convert
	 * @return file: the <code>File<code> object
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
