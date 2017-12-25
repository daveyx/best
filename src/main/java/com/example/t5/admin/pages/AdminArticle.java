package com.example.t5.admin.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.persistence.model.PArticle;
import com.example.service.IArticleAccessService;
import com.example.t5.admin.components.AdminLayout;

@RequiresAuthentication
public class AdminArticle {

	// -----------> services

	@Inject
	private IArticleAccessService articleAccessService;

	// -----------> components

	@Component(id = "template", parameters = {})
	private AdminLayout layoutComponent;

	@InjectComponent("articleform")
    private Form form;

	// -----------> properties

	@Property
	private PArticle pArticle;

	@Property
	private long articleId;

	// -----------> events

	void onActivate(final long articleId) {
		this.articleId = articleId;
	}

	void onPrepareForRender() {
		pArticle = articleAccessService.getArticleById(articleId);
	}

	Object onPassivate() {
		return articleId;
	}

	void onPrepareForSubmit() {
		pArticle = articleAccessService.getArticleById(articleId);
	}

	void onValidateFromArticleForm() {
		System.out.println("--> onValidateFromArticleForm");
		
	}
	
	void onSuccess() {
		System.out.println("--> onSuccess");
		articleAccessService.save(pArticle);
	}
}
