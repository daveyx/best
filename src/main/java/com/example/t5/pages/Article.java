package com.example.t5.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.service.IElasticsearchAccessService;

public class Article {

	// -----------> services

	@Inject
	private IElasticsearchAccessService elasticsearchAccessService;

	// -----------> components

	// -----------> properties

	@Property
	private String articleUuid;

	@Property
	private com.example.elasticsearch.model.Article article;

	// -----------> events

	void onActivate(final String uuid) {
		articleUuid = uuid;
	}

	@SetupRender
	void setup() {
		article = elasticsearchAccessService.getByUuid(articleUuid);
	}

	void onPrepareForRender() {
		
	}
}
