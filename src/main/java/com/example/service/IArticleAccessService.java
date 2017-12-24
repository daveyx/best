package com.example.service;

import com.example.persistence.model.PArticleGroup;

public interface IArticleAccessService {

	PArticleGroup getArticleGroupByName(String name);

	void createArticleGroup(String name);

}
