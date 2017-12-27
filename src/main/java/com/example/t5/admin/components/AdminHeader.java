package com.example.t5.admin.components;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

public class AdminHeader {

	// -----------> services

	@Inject
	private JavaScriptSupport javaScriptSupport;

	
	// -----------> components

	// -----------> properties


	// -----------> events

    
    void afterRender()
    {
    	javaScriptSupport.require("bootstrap/collapse");
    	javaScriptSupport.require("bootstrap/dropdown");
    }
}
