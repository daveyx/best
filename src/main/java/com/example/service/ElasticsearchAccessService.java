package com.example.service;

import org.apache.commons.lang3.StringUtils;
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

	@Override
	public Article getByUuid(final String articleUuid) {
		if (StringUtils.isNotBlank(articleUuid)) {
			return articleRepository.findByUuid(articleUuid);
		}
		return null;
	}
}
