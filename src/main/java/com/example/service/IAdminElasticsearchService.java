package com.example.service;

import com.example.elasticsearch.model.Article;

public interface IAdminElasticsearchService {

	Iterable<Article> getAllArticles();

	int reindexArticles();

}
