package com.example.t5.admin.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.persistence.model.PArticleGroup;
import com.example.service.IArticleAccessService;
import com.example.t5.admin.components.AdminLayout;

@RequiresAuthentication
public class AdminArticleGroup {

	// -----------> services

	@Inject
	private IArticleAccessService articleAccessService;

	// -----------> components

	@Component(id = "template", parameters = {})
	private AdminLayout layoutComponent;

	// -----------> properties

	@Property
	private PArticleGroup pArticleGroup;

	// -----------> events

	void onActivate(final long articleGroupId) {
		pArticleGroup = articleAccessService.getArticleGroupById(articleGroupId);
	}
}
