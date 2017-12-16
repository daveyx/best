package com.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.elasticsearch.repo.TestDataRepository;

@Component
public class Startup {

	private static final Log LOGGER = LogFactory.getLog(Startup.class);

//	@Autowired
//	private TestDataRepository testDataRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
//		initTestData();
		LOGGER.info("-------------> startup finished");
	}

	//
	// ---> private
	//


//	private void initTestData() {
//		final UUID uuid = UUID.fromString(TestData.TEST_UUID);
//		final TestData testDataExisting = testDataRepository.findByUuid(TestData.TEST_UUID);
//		if (testDataExisting != null) {
//			testDataRepository.deleteAll();
//		}
//		final TestData testData = new TestData();
//		testData.setId(uuid);
//		testData.setUuid(TestData.TEST_UUID);
//		testData.setValue("1");
//		final TestData testDataSaved = testDataRepository.save(testData);
//		LOGGER.info("testdata.uuid=" + testDataSaved.getId());
//	}
}
