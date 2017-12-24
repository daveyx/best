package com.example.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.persistence.model.PArticle;
import com.example.persistence.model.PArticleGroup;

public interface PArticleRepository extends JpaRepository<PArticle, Long> {

	List<PArticle> findByArticleGroup(final PArticleGroup articleGroup);
	PArticle findByHeading(final String heading);
}
