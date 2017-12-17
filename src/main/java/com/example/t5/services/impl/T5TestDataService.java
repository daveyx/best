package com.example.t5.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.elasticsearch.model.TestData;
import com.example.elasticsearch.repo.TestDataRepository;
import com.example.t5.services.IT5TestDataService;

public class T5TestDataService implements IT5TestDataService {

	@Inject
	private TestDataRepository testDataRepository;

	@Override
	public TestData getTestDataByUuid(final String uuid) {
		final Page<TestData> testDataPage = testDataRepository.findByUuId(TestData.TEST_UUID, Pageable.unpaged());
		if (testDataPage.getContent().isEmpty() == false) {
			return testDataPage.getContent().get(0);
		}
		return null;
	}

	@Override
	public void saveTestDataByUuid(final TestData testData) {
		testDataRepository.save(testData);
	}
}
