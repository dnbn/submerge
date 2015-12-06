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

	/**
	 * Get the default redirection
	 * 
	 * @return the page name with redirect param
	 */
	protected static String getDefaultRedirect() {
		return Pages.INDEX.getRedirect();
	}

	/**
	 * Get the error redirection
	 * 
	 * @return the page name with redirect param
	 */
	protected static String getErrorRedirect() {
		return Pages.ERROR.getRedirect();
	}

	/**
	 * Get the current request
	 * 
	 * @return the request
	 */
	protected static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * Get the current response
	 * 
	 * @return the response
	 */
	protected static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	/**
	 * Get the external context
	 * 
	 * @return the external context
	 */
	protected static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * Get the messages bundle
	 * 
	 * @return the messages bundle
	 */
	protected static ResourceBundle getBundleMessages() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context, AppConstants.BUNDLE_RESSOURCE.toString());
	}

	/**
	 * Retern the parameter map matching the current request
	 * 
	 * @return the parameter map
	 */
	protected static Map<String, String> getRequestParameterMap() {
		return getExternalContext().getRequestParameterMap();
	}

	/**
	 * Refresh the current page
	 */
	protected static void refresh() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String refreshpage = facesContext.getViewRoot().getViewId();
		ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
		UIViewRoot viewroot = viewHandler.createView(facesContext, refreshpage);
		viewroot.setViewId(refreshpage);
		facesContext.setViewRoot(viewroot);
	}

	/**
	 * Get the current view Id
	 * 
	 * @return the view Id
	 */
	protected static String getViewId() {
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	/**
	 * Get the current view root
	 * 
	 * @return the view root
	 */
	protected static UIViewRoot getViewRoot() {
		return FacesContext.getCurrentInstance().getViewRoot();
	}

}
