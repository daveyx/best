package com.example.t5.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.example.elasticsearch.model.Article;

public class ArticleItemView {

	// -----------> services

	// -----------> components

	// -----------> properties

	@Parameter(required = true)
	@Property
	private Article article;

	// -----------> events

}
