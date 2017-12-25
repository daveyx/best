package com.example.t5.admin.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.persistence.model.PArticleGroup;
import com.example.persistence.repo.PArticleRepository;
import com.example.service.IArticleAccessService;
import com.example.t5.admin.components.AdminLayout;
import com.example.t5.services.ArticlePagedDataSource;

@RequiresAuthentication
public class AdminArticleGroup {

	// -----------> services

	@Inject
	private IArticleAccessService articleAccessService;

	@Inject
	private PArticleRepository pArticleRepository;

	// -----------> components

	@Component(id = "template", parameters = {})
	private AdminLayout layoutComponent;

	// -----------> properties

	@Property
	private PArticleGroup pArticleGroup;

	@Property
    private GridDataSource articles;

	// -----------> events

	void onActivate(final long articleGroupId) {
		pArticleGroup = articleAccessService.getArticleGroupById(articleGroupId);
	}

	Object onPassivate() {
		return pArticleGroup.getId();
	}

	@SetupRender
	void setup() {
		articles = new ArticlePagedDataSource(pArticleRepository, pArticleGroup);		
	}
}
