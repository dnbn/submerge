package com.submerge.web.bean;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.submerge.constant.Pages;

public abstract class AbstractManagedBean {

	protected static String getDefaultRedirect() {
		return Pages.INDEX.getRedirect();
	}

	protected static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	protected static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
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
