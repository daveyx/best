package com.example.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.persistence.model.PArticleGroup;

public interface PArticleGroupRepository extends JpaRepository<PArticleGroup, Long> {

}
