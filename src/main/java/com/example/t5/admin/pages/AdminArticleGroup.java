package com.example.t5.admin.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import com.example.persistence.model.PArticle;
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

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    // -----------> components

	@Component(id = "template", parameters = {})
	private AdminLayout layoutComponent;

	// -----------> properties

	@Property
	private PArticleGroup pArticleGroup;

	@Property
    private GridDataSource articles;

	@Property
    private BeanModel<PArticle> articleModel;

	@Property
	private PArticle article;

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
		articleModel = beanModelSource.createDisplayModel(PArticle.class, messages);
		articleModel.add("action", null);
		articleModel.include("id", "heading", "intro", "image", "action");
		articleModel.get("id").sortable(false);
		articleModel.get("heading").sortable(false);
		articleModel.get("intro").sortable(false);
		articleModel.get("image").sortable(false);
	}
}
