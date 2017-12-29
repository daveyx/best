package com.example.elasticsearch;

import com.example.elasticsearch.model.Article;

public interface IElasticsearchAccessService {

	Iterable<Article> getAllArticles();

	Article getByUuid(final String articleUuid);

}
