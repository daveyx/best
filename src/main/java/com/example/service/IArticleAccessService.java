package com.example.service;

import com.example.persistence.model.PArticle;
import com.example.persistence.model.PArticleGroup;

public interface IArticleAccessService {

	PArticleGroup getArticleGroupByName(final String name);

	void createArticleGroup(final String name);

	PArticle getArticleByHeading(final String heading);

	void createArticle(final PArticleGroup pArticleGroup, final String heading);

}
