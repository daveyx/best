package com.example.t5.services;

import java.util.List;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.persistence.model.PArticle;
import com.example.persistence.model.PArticleGroup;
import com.example.persistence.repo.PArticleRepository;

public class ArticlePagedDataSource implements GridDataSource {

	private final PArticleRepository pArticleRepository;
	private final PArticleGroup pArticleGroup;

	private int startIndex;
	private List<PArticle> articles;

	public ArticlePagedDataSource(final PArticleRepository pArticleRepository, final PArticleGroup pArticleGroup) {
		this.pArticleRepository = pArticleRepository;
		this.pArticleGroup = pArticleGroup;
	}

	@Override
	public int getAvailableRows() {
		return pArticleRepository.countByArticleGroup(pArticleGroup).intValue();
	}

	@Override
	public void prepare(int startIndex, int endIndex, List<SortConstraint> sortConstraints) {
		this.startIndex = startIndex;
		final Pageable pageable = PageRequest.of((endIndex/10), 10);
		this.articles = pArticleRepository.findAll(pageable).getContent();
	}

	@Override
	public Object getRowValue(int index) {
		return articles.get(index - startIndex);
	}

	@Override
	public Class<PArticle> getRowType() {
		return PArticle.class;
	}

}
