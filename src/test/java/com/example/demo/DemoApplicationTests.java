package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.elasticsearch.ElasticsearchConfig;
import com.example.elasticsearch.model.TestData;
import com.example.elasticsearch.repo.TestDataRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ElasticsearchConfig.class)
public class DemoApplicationTests {

	private static final String TEST_UUID = "12341234";

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	private TestDataRepository testDataRepository;

	@Before
	public void before() {
		elasticsearchTemplate.deleteIndex(TestData.class);
		elasticsearchTemplate.createIndex(TestData.class);
	}

	@Test
	public void create() {
		final TestData testData = new TestData();
		testData.setUuId(TEST_UUID);
		testData.setValue("1");
		final TestData testDataSaved = testDataRepository.save(testData);
		assertNotNull(testDataSaved);
		assertEquals(testDataSaved.getValue(), "1");
	}

	@Test
	public void read() {
		create();
		final Page<TestData> testDataPage = testDataRepository.findByUuId(TEST_UUID, Pageable.unpaged());
		final TestData testDataExisting;
		if (testDataPage.getContent().isEmpty() == false) {
			testDataExisting = testDataPage.getContent().get(0);
		} else {
			testDataExisting = null;
		}
		assertNotNull(testDataExisting);
		assertEquals(testDataExisting.getValue(), "1");
	}
}
