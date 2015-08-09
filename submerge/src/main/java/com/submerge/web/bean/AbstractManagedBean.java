package com.submerge.web.bean;

import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.submerge.constant.AppConstants;
import com.submerge.constant.Pages;

public abstract class AbstractManagedBean {

	protected static String getDefaultRedirect() {
		return Pages.INDEX.getRedirect();
	}

	protected static String getErrorRedirect() {
		return Pages.ERROR.getRedirect();
	}

	protected static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	protected static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	protected static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	protected static ResourceBundle getBundleMessages() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context, AppConstants.BUNDLE_RESSOURCE.toString());
	}

	protected static Map<String, String> getRequestParameterMap() {
		return getExternalContext().getRequestParameterMap();
	}

	protected static void refresh() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String refreshpage = facesContext.getViewRoot().getViewId();
		ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
		UIViewRoot viewroot = viewHandler.createView(facesContext, refreshpage);
		viewroot.setViewId(refreshpage);
		facesContext.setViewRoot(viewroot);
	}

	protected static String getViewId() {
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}
}
