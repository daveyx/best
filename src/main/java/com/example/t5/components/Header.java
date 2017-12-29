package com.example.t5.components;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.tynamo.security.services.SecurityService;

import com.example.t5.pages.Index2;

public class Header {

	// -----------> services

	@Inject
	private SecurityService securityService;

	@Inject
	private AlertManager alertManager;
	
	@Inject
	private PageRenderLinkSource prls;

	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private Messages messages;

	// -----------> components

	// -----------> properties

	// -----------> events

	void afterRender() {
		javaScriptSupport.require("bootstrap/collapse");
		javaScriptSupport.require("bootstrap/dropdown");
	}

	@OnEvent(value = "logout")
    Object onLogout()
    {
    	securityService.getSubject().logout();
    	
    	if (securityService.getSubject().isAuthenticated()) {
    		alertManager.warn(messages.get("com.example.header.logoutfailed"));
    	} else {
    		alertManager.success(messages.get("com.example.header.logoutsuccess"));
    	}
    	return prls.createPageRenderLink(Index2.class);
    }
}
