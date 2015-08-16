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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.submerge.exception.InvalidFileException;
import com.submerge.exception.InvalidSRTSubException;
import com.submerge.model.entity.SubtitleProfile;
import com.submerge.service.SubtitleService;
import com.submerge.service.UserService;
import com.submerge.sub.ass.ASSSub;
import com.submerge.sub.config.Font;
import com.submerge.sub.config.SubInput;
import com.submerge.sub.srt.SRTParser;
import com.submerge.sub.srt.SRTSub;
import com.submerge.utils.FileUploadUtils;
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
	 * Parse an uploaded file to srt subtitle and fill the model bean if ok
	 * 
	 * @param event
	 *            : the uploaded srt file
	 */
	public void handleFileUpload(FileUploadEvent event) {	
		ResourceBundle bundle = getBundleMessages();
		FacesMessage msg = null;
		try {
			File file = FileUploadUtils.toFile(event.getFile());
			SRTSub srtSub = SRTParser.parse(file);

			if ("topUpload".equals(event.getComponent().getId())) {
				this.userConfig.setTopSubtitle(srtSub);
			} else {
				this.userConfig.setBottomSubtitle(srtSub);

			}
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, file.getName());

		} catch (InvalidSRTSubException | InvalidFileException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("sub.invalid"), e.getMessage());
		} catch (IOException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.unexpected"), null);
		}
		FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
	}

	/**
	 * Merge 2 srt input files into one .ass returned as StreamedContent
	 * 
	 * @return ass subtitle
	 */
	public StreamedContent getGeneratedFile() {
		SRTSub topSub = this.userConfig.getTopSubtitle();
		SRTSub bottomSub = this.userConfig.getBottomSubtitle();

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

	private static SubInput createSubInput(SRTSub topSubtitle, SubtitleProfile profile, String styleName, int alignment) {
		Font font = new Font();
		font.setColor(profile.getPrimaryColor());
		font.setName(profile.getFontName());
		font.setOutlineColor(profile.getOutlineColor());
		font.setOutlineWidth(profile.getOutlineWidth());
		font.setSize(profile.getFontSize());
		SubInput si = new SubInput(topSubtitle, font);
		si.setStyleName(styleName);
		si.setAlignment(alignment);
		return si;
	}

	private String getFileName() {
		String filename = this.userConfig.getFilename();
		if (StringUtils.isEmpty(filename)) {
			filename = this.userConfig.getTopSubtitle().getFileName();
			filename = FilenameUtils.getBaseName(filename);
		}
		return filename;
	}

	private void updateFilesMessages(boolean renderError) {
		updateFileMessage(this.userConfig.getTopSubtitle(), "index-form:topUpload", renderError);
		updateFileMessage(this.userConfig.getBottomSubtitle(), "index-form:bottomUpload", renderError);
	}

	private static void updateFileMessage(SRTSub sub, String clientId, boolean renderError) {
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
