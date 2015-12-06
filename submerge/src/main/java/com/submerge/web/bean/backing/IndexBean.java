package com.submerge.web.bean.backing;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.exception.InvalidFileException;
import com.submerge.exception.InvalidSubException;
import com.submerge.model.entity.SubtitleProfile;
import com.submerge.service.SubtitleService;
import com.submerge.service.UserService;
import com.submerge.sub.ass.ASSSub;
import com.submerge.sub.config.Font;
import com.submerge.sub.config.SubInput;
import com.submerge.sub.itf.TimedTextFile;
import com.submerge.sub.parser.ParserFactory;
import com.submerge.sub.parser.itf.SubtitleParser;
import com.submerge.utils.FileUtils;
import com.submerge.web.bean.AbstractManagedBean;
import com.submerge.web.bean.model.UserBean;
import com.submerge.web.bean.model.UserSubConfigBean;

@Component("indexBean")
@Scope(value = "request")
public class IndexBean extends AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = -3227108053080225466L;

	private static final transient Logger logger = Logger.getLogger(IndexBean.class.getName());

	@Autowired
	private transient SubtitleService subtitleService;

	@Autowired
	private transient UserService userService;

	@Autowired
	private UserSubConfigBean userConfig;

	@Autowired
	private UserBean userBean;

	// ======================== Public methods ==========================

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
			File file = FileUtils.toFile(uploadedFile);
			String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
			SubtitleParser parser = ParserFactory.getParser(extension);

			replaceSub(componentId, null);

			TimedTextFile sub = parser.parse(file);
			replaceSub(componentId, sub);

			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, file.getName());

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
		if ("topUpload".equals(componentId)) {
			this.userConfig.setTopSubtitle(sub);
		} else {
			this.userConfig.setBottomSubtitle(sub);
		}
	}

	/**
	 * Merge 2 srt input files into one .ass returned as StreamedContent
	 * 
	 * @return the merged ass subtitle
	 */
	public StreamedContent getGeneratedFile() {
		TimedTextFile topSub = this.userConfig.getTopSubtitle();
		TimedTextFile bottomSub = this.userConfig.getBottomSubtitle();

		StreamedContent sc = null;

		if (topSub != null && bottomSub != null) {
			SubInput top = createSubInput(topSub, this.userConfig.getProfileTop(), "Top", 8);
			SubInput bottom = createSubInput(bottomSub, this.userConfig.getProfileBottom(), "Bottom", 2);
			ASSSub sub = this.subtitleService.mergeToAss(top, bottom);

			logger.log(Level.FINE, "File generated from : " + this.userConfig.toString());
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
	 * Create a SubInput object from a ParsableSubtitle, matching a SubtitleProfile
	 * 
	 * @param subtitle: the parsed subtitle
	 * @param profile: the subtitle profile
	 * @param styleName: the name of the style
	 * @param alignment: the alignment of the subtitle (top, bottom,...)
	 * @return the SubInput object
	 */
	private static SubInput createSubInput(TimedTextFile subtitle, SubtitleProfile profile, String styleName,
			int alignment) {
		Font font = new Font();
		font.setColor(profile.getPrimaryColor());
		font.setName(profile.getFontName());
		font.setOutlineColor(profile.getOutlineColor());
		font.setOutlineWidth(profile.getOutlineWidth());
		font.setSize(profile.getFontSize());
		SubInput si = new SubInput(subtitle, font);
		si.setStyleName(styleName);
		si.setAlignment(alignment);
		return si;
	}

	/**
	 * Get the final filename of the generated .ass subtitle
	 * 
	 * @return the name of the top subtitle
	 */
	private String getFileName() {
		String filename = this.userConfig.getFilename();
		if (StringUtils.isEmpty(filename)) {
			filename = this.userConfig.getTopSubtitle().getFileName();
			filename = FilenameUtils.getBaseName(filename);
		}
		return filename;
	}

	/**
	 * Update the messages controles.
	 * 
	 * @param renderError: if true, an error message is rendered if the subtitle is null
	 */
	private void updateFilesMessages(boolean renderError) {
		updateFileMessage(this.userConfig.getTopSubtitle(), "index-form:topUpload", renderError);
		updateFileMessage(this.userConfig.getBottomSubtitle(), "index-form:bottomUpload", renderError);
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

}
