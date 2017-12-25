package com.example.t5.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.elasticsearch.model.Article;
import com.example.service.IElasticsearchAccessService;

public class Index2 {

	// -----------> services

	@Inject
	private IElasticsearchAccessService elasticsearchAccessService;

	// -----------> components

	// -----------> properties

	@Property
	private Iterable<Article> articles;

	@Property
	private Article article;

	// -----------> events

	@SetupRender
	void setup() {
		articles = elasticsearchAccessService.getAllArticles();
	}
}
