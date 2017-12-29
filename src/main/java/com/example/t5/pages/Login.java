package com.example.t5.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

@RequiresAuthentication
public class Login {

	// -----------> services

    // -----------> components

	// -----------> properties
	
	@Property
	private String successURL;
	
	// -----------> events

	@SetupRender
	void setup() {
	}
}
