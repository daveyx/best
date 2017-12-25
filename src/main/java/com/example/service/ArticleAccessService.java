package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.persistence.model.PArticle;
import com.example.persistence.model.PArticleGroup;
import com.example.persistence.repo.PArticleGroupRepository;
import com.example.persistence.repo.PArticleRepository;

public class ArticleAccessService implements IArticleAccessService{

	@Autowired
	private PArticleRepository pArticleRepository;

	@Autowired
	private PArticleGroupRepository pArticleGroupRepository;

	@Override
	public PArticleGroup getArticleGroupByName(final String name) {
		return pArticleGroupRepository.findByName(name);
	}

	@Override
	public void createArticleGroup(final String name) {
		if (pArticleGroupRepository.findByName(name) != null) {
			throw new IllegalStateException("ArticleGroup " + name + " already exists");
		}
		final PArticleGroup pArticleGroup = new PArticleGroup();
		pArticleGroup.setName(name);
		pArticleGroupRepository.save(pArticleGroup);
	}

	@Override
	public PArticle getArticleByHeading(final String heading) {
		return pArticleRepository.findByHeading(heading);
	}

	@Override
	public void createArticle(final PArticleGroup pArticleGroup, final String heading) {
		if (pArticleRepository.findByHeading(heading) != null) {
			throw new IllegalStateException("Article " + heading + " already exists");
		}
		final PArticle pArticle = new PArticle();
		pArticle.setHeading(heading);
		pArticle.setArticleGroup(pArticleGroup);
		pArticleRepository.save(pArticle);
	}

	@Override
	public List<PArticleGroup> getAllArticleGroups() {
		return pArticleGroupRepository.findAll();
	}
}
