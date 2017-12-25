package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.elasticsearch.model.Article;
import com.example.elasticsearch.repo.ArticleRepository;

public class ElasticsearchAccessService implements IElasticsearchAccessService {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Override
	public Iterable<Article> getAllArticles() {
		return articleRepository.findAll();
	}

}
