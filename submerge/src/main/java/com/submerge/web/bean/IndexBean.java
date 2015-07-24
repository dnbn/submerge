package com.submerge.web.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
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

import com.submerge.constant.AppConstants;
import com.submerge.exception.InvalidFileException;
import com.submerge.exception.InvalidSRTSubException;
import com.submerge.service.SubtitleService;
import com.submerge.sub.ass.ASSSub;
import com.submerge.sub.config.SubConfig;
import com.submerge.sub.config.SubInput;
import com.submerge.sub.srt.SRTParser;
import com.submerge.sub.srt.SRTSub;
import com.submerge.utils.FileUploadUtils;

@Component("indexBean")
@Scope(value = "session")
public class IndexBean implements Serializable {

	private static final long serialVersionUID = -3227108053080225466L;

	private SubConfig sbmConf = new SubConfig();

	@Autowired
	private transient SubtitleService subtitleService;

	// ======================== Public methods ==========================

	@PostConstruct
	public void init() {
		SubInput topSub = this.sbmConf.getTopSubtitle();
		SubInput bottomSub = this.sbmConf.getBottomSubtitle();

		topSub.setStyleName("Top");
		topSub.setAlignment(8);
		bottomSub.setStyleName("Bottom");
		bottomSub.setAlignment(2);
	}

	/**
	 * Parse an uploaded file to srt subtitle and call the setter if ok
	 * 
	 * @param event
	 *            : the uploaded srt file
	 */
	public void handleFileUpload(FileUploadEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();
		ResourceBundle bundle = app.getResourceBundle(context, AppConstants.BUNDLE_RESSOURCE.toString());
		FacesMessage msg = null;
		try {
			File file = FileUploadUtils.toFile(event.getFile());
			SRTSub srtSub = SRTParser.parse(file);

			if ("topUpload".equals(event.getComponent().getId())) {
				this.sbmConf.getTopSubtitle().setSub(srtSub);
			} else {
				this.sbmConf.getBottomSubtitle().setSub(srtSub);

			}
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, file.getName());

		} catch (InvalidSRTSubException | InvalidFileException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("sub.invalid"), e.getMessage());
		} catch (IOException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.unexpected"), null);
		}
		context.addMessage(event.getComponent().getClientId(), msg);
	}

	/**
	 * Merge 2 srt input files into one .ass returned as StreamedContent
	 * 
	 * @return ass subtitle
	 */
	public StreamedContent getGeneratedFile() {
		StreamedContent sc = null;
		SubInput top = this.sbmConf.getTopSubtitle();
		SubInput bottom = this.sbmConf.getBottomSubtitle();

		if (top.getSub() != null && bottom.getSub() != null) {
			ASSSub sub = this.subtitleService.mergeToAss(top, bottom);

			String filename = this.sbmConf.getTargetFilename();
			if (StringUtils.isEmpty(filename)) {
				filename = this.sbmConf.getTopSubtitle().getSub().getFileName();
				filename = FilenameUtils.getBaseName(filename);
			}

			sc = new DefaultStreamedContent(sub.toInputStream(), "text/plain", filename + ".ass");
		}

		updateFilesMessages(true);
		return sc;
	}

	public void onPageLoad() {
		updateFilesMessages(false);
	}

	// ===================== private methods start =====================

	private void updateFilesMessages(boolean renderError) {
		updateFileMessage(this.sbmConf.getTopSubtitle().getSub(), "index-form:topUpload", renderError);
		updateFileMessage(this.sbmConf.getBottomSubtitle().getSub(), "index-form:bottomUpload", renderError);
	}

	private static void updateFileMessage(SRTSub sub, String clientId, boolean renderError) {
		FacesContext context = FacesContext.getCurrentInstance();

		if (sub == null) {
			if (renderError) {
				Application app = context.getApplication();
				ResourceBundle bundle = app.getResourceBundle(context, AppConstants.BUNDLE_RESSOURCE.toString());
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, bundle.getString("sub.empty"));
				context.addMessage(clientId, msg);
			}
		} else {
			// Remove previous messages for this client id
			for (Iterator<FacesMessage> it = context.getMessages(clientId); it.hasNext();) {
				it.next();
				it.remove();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, sub.getFileName());
			context.addMessage(clientId, msg);
		}

	}

	// ===================== getter and setter start =====================

	public SubConfig getSbmConf() {
		return this.sbmConf;
	}

	public void setSbmConf(SubConfig sbmConf) {
		this.sbmConf = sbmConf;
	}

}
