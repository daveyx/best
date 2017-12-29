package com.example.elasticsearch;

import com.example.elasticsearch.model.Article;
import com.example.util.Tuple2;

public interface IAdminElasticsearchService {

	Iterable<Article> getAllArticles();

	Tuple2<Integer, Integer> reindexArticles();

}
