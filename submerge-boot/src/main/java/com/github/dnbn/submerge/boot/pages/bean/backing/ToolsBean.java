package com.github.dnbn.submerge.boot.pages.bean.backing;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.dnbn.submerge.api.SubmergeAPI;
import com.github.dnbn.submerge.api.parser.ParserFactory;
import com.github.dnbn.submerge.api.subtitle.ass.ASSSub;
import com.github.dnbn.submerge.api.subtitle.common.TimedTextFile;
import com.github.dnbn.submerge.api.subtitle.config.SimpleSubConfig;
import com.github.dnbn.submerge.api.subtitle.srt.SRTSub;
import com.github.dnbn.submerge.api.utils.FileUtils;
import com.github.dnbn.submerge.boot.model.SubtitleProfile;
import com.github.dnbn.submerge.boot.pages.bean.AbstractManagedBean;
import com.github.dnbn.submerge.boot.pages.bean.model.UserBean;
import com.github.dnbn.submerge.boot.pages.bean.model.UserSubConfigBean;
import com.github.dnbn.submerge.boot.service.UserService;
import com.github.dnbn.submerge.boot.utils.ProfileUtils;

@Component("toolsBean")
@Scope(value = "session")
public class ToolsBean extends AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = -4565775891674696585L;

	private static final transient Logger logger = Logger.getLogger(ToolsBean.class.getName());

	@Autowired
	private transient UserService userService;

	private UploadedFile uploadedFile;

	@Autowired
	private UserSubConfigBean userConfig;

	private Double sourceFramerate;

	private Double destinationFramerate;

	@Autowired
	private UserBean userBean;

	private String previewLanguage;

	// ======================== Public methods ==========================

	/**
	 * Parse a subtitle and convert it to ASS
	 * 
	 */
	public void convertAss() {

		String fullName = this.uploadedFile.getFileName();

		try {
			String filename = FilenameUtils.getName(fullName);
			String extension = FilenameUtils.getExtension(fullName);

			TimedTextFile ttf = ParserFactory.getParser(extension).parse(this.uploadedFile.getInputStream(), filename);
			SubtitleProfile profile = this.userConfig.getProfileSimple();

			SimpleSubConfig subInput = ProfileUtils.createSubConfig(ttf, profile, "Default");

			SubmergeAPI convert = new SubmergeAPI();
			ASSSub ass = convert.toASS(subInput);

			String destFileName = StringUtils.removeEnd(filename, extension) + ".ass";

			writeString(destFileName, ass.toString());

			saveUserState();

			logger.log(Level.FINE, "File : " + filename + " converted to ASS");
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);

			if (this.uploadedFile != null) {
				logger.log(Level.SEVERE, "Cannot convert " + fullName + " to ass : " + e.getMessage());
			}
		} finally {
			this.uploadedFile = null;
		}
	}

	/**
	 * Parse a subtitle and convert it to SRT
	 * 
	 */
	public void convertSrt() {

		String fullName = this.uploadedFile.getFileName();

		try {
			String filename = FilenameUtils.getName(fullName);
			String extension = FilenameUtils.getExtension(fullName);

			TimedTextFile ttf = ParserFactory.getParser(extension).parse(this.uploadedFile.getInputStream(), filename);
			SRTSub srtSub = new SubmergeAPI().toSRT(ttf);

			String destFileName = StringUtils.removeEnd(filename, extension) + ".srt";

			writeString(destFileName, srtSub.toString());

			saveUserState();

			logger.log(Level.FINE, "File : " + filename + " converted to SRT");
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);

			if (this.uploadedFile != null) {
				logger.log(Level.SEVERE,
						"Cannot convert " + this.uploadedFile.getFileName() + " to srt : " + e.getMessage());
			}

		} finally {
			this.uploadedFile = null;
		}
	}

	/**
	 * Change the encoding charset of a file to UTF-8
	 * 
	 */
	public void convertUtf8() {

		try {
			if (StringUtils.isEmpty(this.uploadedFile.getFileName())) {
				throw new RuntimeException("Empty file");
			}
			byte[] bytes = IOUtils.toByteArray(this.uploadedFile.getInputStream());
			String encoding = FileUtils.guessEncoding(bytes);

			StringBuffer sbf = new StringBuffer();
			StringBuilder sb = new StringBuilder(sbf);

			try (ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
					InputStreamReader isr = new InputStreamReader(bai, encoding);
					BufferedReader br = new BufferedReader(isr)) {

				for (int c; (c = br.read()) != -1;) {
					sb.append((char) c);
				}
			}

			String filename = FilenameUtils.getName(this.uploadedFile.getFileName());
			writeString(filename, sb.toString());

			saveUserState();

			logger.log(Level.FINE, "File encoding changed from " + encoding + " - file : " + filename);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);

			if (this.uploadedFile != null) {
				logger.log(Level.SEVERE,
						"Cannot convert " + this.uploadedFile.getFileName() + " to UTF-8 : " + e.getMessage());
			}
		} finally {
			this.uploadedFile = null;
		}

	}

	/**
	 * Change the framerate of a subtitle
	 * 
	 */
	public void convertFramerate() {

		String fullName = this.uploadedFile.getFileName();

		try {
			if (StringUtils.isEmpty(this.uploadedFile.getFileName())) {
				throw new RuntimeException("Empty file");
			}

			String filename = FilenameUtils.getName(fullName);
			String extension = FilenameUtils.getExtension(fullName);

			TimedTextFile ttf = ParserFactory.getParser(extension).parse(this.uploadedFile.getInputStream(), filename);
			new SubmergeAPI().convertFramerate(ttf, this.sourceFramerate, this.destinationFramerate);

			writeString(fullName, ttf.toString());

			logger.log(Level.FINE, "File : " + filename + " framerate change from " + this.sourceFramerate + " to "
					+ this.destinationFramerate);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);

			if (this.uploadedFile != null) {
				logger.log(Level.SEVERE,
						"Cannot change the framerate of " + this.uploadedFile.getFileName() + " : " + e.getMessage());
			}

		} finally {
			this.uploadedFile = null;
		}
	}

	/**
	 * Save user profiles in database if the user is logged
	 */
	private void saveUserState() {
		if (this.userBean.isLogged()) {
			this.userService.save(this.userBean.getUser());
		}
	}

	/**
	 * Update the language of the bottom preview
	 */
	public void updatePreviewLanguage() {

		String language = getRequestParameterMap().get("languageToSet");
		this.userConfig.getProfileSimple().setLanguage(language);
	}

	/**
	 * Method called each time the index page is loaded
	 */
	public void onPageLoad() {

		SubtitleProfile profile = this.userConfig.getProfileSimple();
		ProfileUtils.initProfiles(profile);

		this.previewLanguage = this.userConfig.getProfileSimple().getLanguage();

		if (StringUtils.isEmpty(this.previewLanguage)) {
			this.previewLanguage = this.userBean.getLanguage();
			this.userConfig.getProfileSimple().setLanguage(this.previewLanguage);
		}
	}

	// ======================== Private methods ==========================

	/**
	 * Write a string as a response
	 * 
	 * @param filename the filename
	 * @param message  the string to write
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private void writeString(String filename, String message) throws IOException, UnsupportedEncodingException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = getExternalContext();

		externalContext.responseReset();
		externalContext.setResponseContentType(this.uploadedFile.getContentType());
		externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

		OutputStream output = externalContext.getResponseOutputStream();
		output.write(message.getBytes("UTF-8"));

		facesContext.responseComplete();
	}

	// ======================== GETTER and SETTER methods ==========================

	public UploadedFile getFile() {

		return this.uploadedFile;
	}

	public void setFile(UploadedFile uploadedFile) {

		this.uploadedFile = uploadedFile;
	}

	public String getPreviewLanguage() {
		return this.previewLanguage;
	}

	public void setPreviewLanguage(String previewLanguage) {
		this.previewLanguage = previewLanguage;
	}

	public String getPreview() {

		Locale locale = new Locale(this.previewLanguage);
		return getBundleMessages(locale).getString("sub.preview");
	}

	public Double getSourceFramerate() {
		return this.sourceFramerate;
	}

	public void setSourceFramerate(Double sourceFramerate) {
		this.sourceFramerate = sourceFramerate;
	}

	public Double getDestinationFramerate() {
		return this.destinationFramerate;
	}

	public void setDestinationFramerate(Double destinationFramerate) {
		this.destinationFramerate = destinationFramerate;
	}

}
