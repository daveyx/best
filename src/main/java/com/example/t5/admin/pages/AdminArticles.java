package com.example.t5.admin.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.Component;

import com.example.t5.admin.components.AdminLayout;

@RequiresAuthentication
public class AdminArticles {
	
	// -----------> services

	
	
	// -----------> components
	
	@Component(id = "template", parameters = {})
	private AdminLayout layoutComponent;
	
	
	// -----------> properties
	

	// -----------> events

}
