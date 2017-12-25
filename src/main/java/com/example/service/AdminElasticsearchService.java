package com.example.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import com.example.elasticsearch.model.Article;
import com.example.elasticsearch.repo.ArticleRepository;
import com.example.persistence.model.PArticle;
import com.example.persistence.repo.PArticleRepository;

public class AdminElasticsearchService implements IAdminElasticsearchService {

	private static final Log LOGGER = LogFactory.getLog(AdminElasticsearchService.class);
	
	private static final int ARTICLE_PAGE_COUNT = 10;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private PArticleRepository pArticleRepository;

	@Override
	public Iterable<Article> getAllArticles() {
		return articleRepository.findAll();
	}

	@Override
	public int reindexArticles() {
		LOGGER.info("reindexArticles...");
		deleteAndCreateNew();
		int pageIndex = 0;
		int itemCount = 0;
		int totalArticleCount = 0;
		do {
			LOGGER.info("pageIndex=" + pageIndex);
			final Pageable page = PageRequest.of(pageIndex, ARTICLE_PAGE_COUNT);
			final Page<PArticle> articles = pArticleRepository.findAll(page);
			for (final PArticle pArticle : articles) {
				if (pArticle.isPublished()) {
					articleRepository.save(convert(pArticle));
				}
			}
			itemCount = articles.getContent().size();
			totalArticleCount += itemCount;
			LOGGER.info("reindexArticles processed " + itemCount + " articles");
			pageIndex++;
		} while (itemCount == ARTICLE_PAGE_COUNT);
		LOGGER.info("reindexArticles finished");
		return totalArticleCount;
	}

	//
	// ---> private
	//

	private void deleteAndCreateNew() {
		elasticsearchTemplate.deleteIndex(Article.class);
		elasticsearchTemplate.createIndex(Article.class);
	}

	private Article convert(final PArticle pArticle) {
		final Article article = new Article();
		article.setArticleGroup(pArticle.getArticleGroup().getName());
		article.setHeading(pArticle.getHeading());
		article.setIntro(pArticle.getIntro());
		article.setImage(pArticle.getImage());
		article.setDatePublished(pArticle.getDatePublished());

		return article;
	}
}
