package com.submerge.web.pages.bean.backing;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.sub.convert.SubtitleConverter;
import com.submerge.sub.object.ass.ASSSub;
import com.submerge.sub.object.config.SubInput;
import com.submerge.sub.object.itf.TimedTextFile;
import com.submerge.sub.parser.ParserFactory;
import com.submerge.sub.parser.exception.InvalidFileException;
import com.submerge.sub.parser.exception.InvalidSubException;
import com.submerge.sub.parser.itf.SubtitleParser;
import com.submerge.web.constant.SupportedLocales;
import com.submerge.web.model.entity.SubtitleProfile;
import com.submerge.web.pages.bean.AbstractManagedBean;
import com.submerge.web.pages.bean.model.UserBean;
import com.submerge.web.pages.bean.model.UserSubConfigBean;
import com.submerge.web.service.UserService;
import com.submerge.web.utils.ProfileUtils;

@Component("indexBean")
@Scope(value = "request")
public class IndexBean extends AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = -3227108053080225466L;

	private static final transient Logger logger = Logger.getLogger(IndexBean.class.getName());

	@Autowired
	private transient UserService userService;

	@Autowired
	private UserSubConfigBean userConfig;

	@Autowired
	private UserBean userBean;

	private String languageOne;

	private String languageTwo;

	// ======================== Public methods ==========================

	@PostConstruct
	public void doInit() {

		SubtitleProfile one = this.userConfig.getProfileOne();
		SubtitleProfile two = this.userConfig.getProfileTwo();

		if (one.getAlignment() == 0) {
			two.setAlignmentOffset(4);
		}

		ProfileUtils.initProfiles(one, two);

		updatePreviewLanguages();
	}

	/**
	 * Parse an uploaded file and fill the model bean if ok
	 * 
	 * @param event: the uploaded file
	 */
	public void handleFileUpload(FileUploadEvent event) {

		ResourceBundle bundle = getBundleMessages();
		FacesMessage msg = null;
		try {
			String componentId = event.getComponent().getId();

			UploadedFile uploadedFile = event.getFile();
			String filename = FilenameUtils.getName(uploadedFile.getFileName());
			String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
			SubtitleParser parser = ParserFactory.getParser(extension);

			replaceSub(componentId, null);

			TimedTextFile sub = parser.parse(uploadedFile.getInputstream(), filename);
			replaceSub(componentId, sub);

			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, filename);

		} catch (InvalidSubException | InvalidFileException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("sub.invalid"), e.getMessage());
		} catch (IOException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.unexpected"), null);
		}
		FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
	}

	/**
	 * Replace the subtitle in session
	 * 
	 * @param componentId: the id of the component
	 * @param sub the subtitle to set
	 */
	private void replaceSub(String componentId, TimedTextFile sub) {

		if ("uploadOne".equals(componentId)) {
			this.userConfig.setFirstSubtitle(sub);
		} else {
			this.userConfig.setSecondSubtitle(sub);
		}
	}

	/**
	 * Merge 2 srt input files into one .ass returned as StreamedContent
	 * 
	 * @return the merged ass subtitle
	 */
	public StreamedContent getGeneratedFile() {

		TimedTextFile subOne = this.userConfig.getFirstSubtitle();
		TimedTextFile subTwo = this.userConfig.getSecondSubtitle();

		StreamedContent sc = null;

		if (subOne != null && subTwo != null) {
			SubInput one = ProfileUtils.createSubInput(subOne, this.userConfig.getProfileOne(), "One");
			SubInput two = ProfileUtils.createSubInput(subTwo, this.userConfig.getProfileTwo(), "Two");

			// If both subs have the same position, add margin to the first one
			if (one.getAlignment() == two.getAlignment()) {
				one.setVerticalMargin(40);
			}
			ASSSub sub = new SubtitleConverter().mergeToAss(one, two);

			logger.log(Level.FINE, "Merged: " + this.userConfig.toString());
			sc = new DefaultStreamedContent(sub.toInputStream(), "text/plain", getFileName() + ".ass");
		}

		saveState();
		updateFilesMessages(true);
		return sc;
	}

	/**
	 * Method called each time the index page is loaded
	 */
	public void onPageLoad() {

		updateFilesMessages(false);
		updatePreviewLanguages();
	}

	/**
	 * Update the language of the first preview
	 */
	public void updatePreviewLanguageOne() {

		String language = getRequestParameterMap().get("languageToSet");
		this.userConfig.getProfileOne().setLanguage(language);
	}

	/**
	 * Update the language of the second preview
	 */
	public void updatePreviewLanguageTwo() {

		String language = getRequestParameterMap().get("languageToSet");
		this.userConfig.getProfileTwo().setLanguage(language);
	}

	// ===================== private methods start =====================

	/**
	 * Save user profiles in database if the user is logged
	 */
	private void saveState() {
		if (this.userBean.isLogged()) {
			this.userService.save(this.userBean.getUser());
		}
	}

	/**
	 * Get the final filename of the generated .ass subtitle
	 * 
	 * @return the name of the first subtitle
	 */
	private String getFileName() {

		String filename = this.userConfig.getFilename();
		if (StringUtils.isEmpty(filename)) {
			filename = this.userConfig.getFirstSubtitle().getFileName();
			filename = FilenameUtils.getBaseName(filename);
		} else {
			Map<String, String> substitutes = new HashMap<>();

			String oneN = FilenameUtils.getBaseName(this.userConfig.getFirstSubtitle().getFileName());
			String twoN = FilenameUtils.getBaseName(this.userConfig.getSecondSubtitle().getFileName());

			substitutes.put("one", oneN);
			substitutes.put("two", twoN);
			substitutes.put("baseOne", StringUtils.substringBeforeLast(oneN, "."));
			substitutes.put("baseTwo", StringUtils.substringBeforeLast(twoN, "."));

			StrSubstitutor substitutor = new StrSubstitutor(substitutes);
			filename = substitutor.replace(filename);
		}
		return filename;
	}

	/**
	 * Update the messages controles.
	 * 
	 * @param renderError: if true, an error message is rendered if the subtitle is null
	 */
	private void updateFilesMessages(boolean renderError) {

		updateFileMessage(this.userConfig.getFirstSubtitle(), "index-form:uploadOne", renderError);
		updateFileMessage(this.userConfig.getSecondSubtitle(), "index-form:uploadTwo", renderError);
	}

	/**
	 * Update the messages controles.
	 * 
	 * @param sub: the subtitle file to set a message for
	 * @param clientId: the clientId of the message corresponding to the sub
	 * @param renderError: if true, an error message is rendered if the subtitle is null
	 */
	private static void updateFileMessage(TimedTextFile sub, String clientId, boolean renderError) {
		FacesContext context = FacesContext.getCurrentInstance();

		if (sub == null) {
			if (renderError) {
				ResourceBundle bundle = getBundleMessages();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, bundle.getString("sub.empty"));
				context.addMessage(clientId, msg);
			}
		} else {
			for (Iterator<FacesMessage> it = context.getMessages(clientId); it.hasNext();) {
				it.next();
				it.remove();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, sub.getFileName());
			context.addMessage(clientId, msg);
		}
	}

	/**
	 * Define the default languages of the previews
	 */
	private void updatePreviewLanguages() {

		this.languageTwo = this.userConfig.getProfileTwo().getLanguage();
		this.languageOne = this.userConfig.getProfileOne().getLanguage();

		if (StringUtils.isEmpty(this.languageTwo)) {
			this.languageTwo = this.userBean.getLanguage();
		}

		if (StringUtils.isEmpty(this.languageOne)) {
			if (this.languageTwo.equals(SupportedLocales.ENGLISH.getLanguage())) {
				this.languageOne = SupportedLocales.FRENCH.getLanguage();
			} else {
				this.languageOne = SupportedLocales.ENGLISH.getLanguage();
			}
		}
	}

	// ======================== GETTER and SETTER methods ==========================

	public String getLanguageOne() {

		return this.languageOne;
	}

	public void setLanguageOne(String languageOne) {

		this.languageOne = languageOne;
	}

	public String getLanguageTwo() {

		return this.languageTwo;
	}

	public void setLanguageBottom(String languageBottom) {

		this.languageTwo = languageBottom;
	}

	public String getPreviewOne() {

		Locale locale = new Locale(this.languageOne);
		return getBundleMessages(locale).getString("sub.preview");
	}

	public String getPreviewTwo() {
		Locale locale = new Locale(this.languageTwo);
		return getBundleMessages(locale).getString("sub.preview");
	}
}
