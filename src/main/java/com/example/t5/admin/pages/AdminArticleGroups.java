package com.example.t5.admin.pages;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.example.persistence.model.PArticleGroup;
import com.example.service.IArticleAccessService;
import com.example.t5.admin.components.AdminLayout;

@RequiresAuthentication
public class AdminArticleGroups {

	// -----------> services

	@Inject
	private IArticleAccessService articleAccessService;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	// -----------> components

	@Component(id = "template", parameters = {})
	private AdminLayout layoutComponent;

	// -----------> properties

	@Property
	private List<PArticleGroup> articleGroups;

	@Property
	private PArticleGroup looparticleGroup;
	
	// -----------> events

	@SetupRender
	void setup() {
		articleGroups = articleAccessService.getAllArticleGroups();
	}

	void afterRender() {
		javaScriptSupport.require("bootstrap/modal");
	}

}
