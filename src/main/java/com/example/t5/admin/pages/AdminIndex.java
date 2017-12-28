package com.example.t5.admin.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.example.t5.admin.components.AdminLayout;

@RequiresAuthentication
@RequiresRoles("admin")
public class AdminIndex {

	// -----------> services

	@Inject
	private JavaScriptSupport javaScriptSupport;

	// -----------> components

	@Component(id = "template", parameters = {})
	private AdminLayout layoutComponent;

	// -----------> properties

	// -----------> events

	void afterRender() {
		javaScriptSupport.require("bootstrap/modal");
	}
}
