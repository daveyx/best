package com.example.elasticsearch.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.elasticsearch.model.Article;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

	Article findByUuid(final String uuid);
}
