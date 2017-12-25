package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.persistence.model.PArticle;
import com.example.persistence.model.PArticleGroup;
import com.example.persistence.repo.PArticleGroupRepository;
import com.example.persistence.repo.PArticleRepository;

public class PArticleAccessService implements IPArticleAccessService {

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
		createArticle(true, null, pArticleGroup, heading);
	}

	@Override
	public void createArticle(final PArticleGroup pArticleGroup, final String author, final String heading, final String image) {
		final PArticle pArticle = createArticle(false, author, pArticleGroup, heading);
		pArticle.setImage(image);
		save(pArticle);
	}

	@Override
	public List<PArticleGroup> getAllArticleGroups() {
		return pArticleGroupRepository.findAll();
	}

	@Override
	public PArticleGroup getArticleGroupById(long articleGroupId) {
		return pArticleGroupRepository.findById(articleGroupId).get();
	}

	@Override
	public PArticle getArticleById(final long articleId) {
		return pArticleRepository.findById(articleId).get();
	}

	@Override
	public PArticle save(final PArticle pArticle) {
		return pArticleRepository.save(pArticle);
	}

	//
	// ---> private
	//

	private PArticle createArticle(final boolean save, final String author, final PArticleGroup pArticleGroup, final String heading) {
		if (pArticleRepository.findByHeading(heading) != null) {
			throw new IllegalStateException("Article " + heading + " already exists");
		}
		final PArticle pArticle = new PArticle();
		pArticle.setAuthor(author);
		pArticle.setHeading(heading);
		pArticle.setArticleGroup(pArticleGroup);
		if (save) {
			return save(pArticle);
		}
		return pArticle;
	}
}
