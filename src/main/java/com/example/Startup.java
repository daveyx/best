package com.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.elasticsearch.model.TestData;
import com.example.elasticsearch.repo.TestDataRepository;
import com.example.persistence.model.PArticle;
import com.example.persistence.model.PArticleGroup;
import com.example.persistence.model.PUserAccount;
import com.example.persistence.model.PUserData;
import com.example.persistence.repo.PArticleGroupRepository;
import com.example.persistence.repo.PArticleRepository;
import com.example.persistence.repo.PUserAccountRepository;
import com.example.service.IPArticleAccessService;

@Component
public class Startup {

	private static final Log LOGGER = LogFactory.getLog(Startup.class);

	@Autowired
	private TestDataRepository testDataRepository;

	@Autowired
	private IPArticleAccessService articleAccessService;

	@Autowired
	private PArticleGroupRepository pArticleGroupRepository;

	@Autowired
	private PArticleRepository pArticleRepository;

	@Autowired
	private PUserAccountRepository pUserAccountRepository;

	@Autowired
	private PasswordService passwordService;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		initTestData();
		initTestArticleData();
		createAccount();
		LOGGER.info("-------------> startup finished");
	}

	public void createAccount() {
		final PUserAccount existingAccount = pUserAccountRepository.findByEmail("user@email.com");
		if (existingAccount != null) {
			return;
		}
		final PUserData pUserData = new PUserData();
		pUserData.setFirstName("fName");
		pUserData.setLastName("lName");
		final PUserAccount pUserAccount = new PUserAccount();
		pUserAccount.setEmail("user@email.com");
		pUserAccount.setUserData(pUserData);
		pUserAccount.setPassword(passwordService.encryptPassword("asdf"));
		final PUserAccount pUserAccountSaved = pUserAccountRepository.save(pUserAccount);
		if (pUserAccountSaved == null) {
			throw new IllegalStateException("error creating default account");
		}
		LOGGER.info("save useraccount done");
	}

	//
	// ---> private
	//

	private void initTestData() {
		final String uuid = TestData.TEST_UUID;
		final Page<TestData> testDataPage = testDataRepository.findByUuId(TestData.TEST_UUID, Pageable.unpaged());
		final TestData testDataExisting;
		if (testDataPage.getContent().isEmpty() == false) {
			testDataExisting = testDataPage.getContent().get(0);
		} else {
			testDataExisting = null;
		}
		if (testDataExisting != null) {
			testDataRepository.deleteAll();
		}
		final TestData testData = new TestData();
		testData.setUuId(uuid);
		testData.setValue("1");
		final TestData testDataSaved = testDataRepository.save(testData);
		LOGGER.info("testdata.uuid=" + testDataSaved.getId());
	}

	private void initTestArticleData() {
		PArticleGroup pArticleGroup = new PArticleGroup();
		pArticleGroup.setName("ArticleGroup1");
		pArticleGroup = pArticleGroupRepository.save(pArticleGroup);

		for (int i = 0; i < 50; i++) {
			articleAccessService.createArticle(pArticleGroup, "Arthur Author1", "article" + i + " in group1",
					"https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/Anonymous_emblem.svg/240px-Anonymous_emblem.svg.png");
			if (i % 3 == 0) {
				final PArticle pArticle = pArticleRepository.findByHeading("article" + i + " in group1");
				if (pArticle != null) {
					pArticle.setPublished(true);
					pArticleRepository.save(pArticle);
				}
			}
		}

		PArticleGroup pArticleGroup2 = new PArticleGroup();
		pArticleGroup2.setName("ArticleGroup2");
		pArticleGroup2 = pArticleGroupRepository.save(pArticleGroup2);
		articleAccessService.createArticle(pArticleGroup2, "Arthur Author2", "article" + 1 + " in group2",
				"https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Skull_and_crossbones.svg/250px-Skull_and_crossbones.svg.png");

		PArticle pArticle = pArticleRepository.findByHeading("article" + 1 + " in group2");
		if (pArticle != null) {
			pArticle.setPublished(true);
			pArticleRepository.save(pArticle);
		}
		
		articleAccessService.createArticle(pArticleGroup2, "Arthur Author2", "article" + 2 + " in group2",
				"https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Skull_and_crossbones.svg/250px-Skull_and_crossbones.svg.png");
		pArticle = pArticleRepository.findByHeading("article" + 2 + " in group2");
		if (pArticle != null) {
			pArticle.setPublished(true);
			pArticleRepository.save(pArticle);
		}
	}
}
