package com.example.elasticsearch.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.elasticsearch.model.TestData;

public interface TestDataRepository extends ElasticsearchRepository<TestData, String> {

	Page<TestData> findByAuthorsName(String name, Pageable pageable);

	@Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
	Page<TestData> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);
}
