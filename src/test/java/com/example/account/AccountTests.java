package com.example.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.DemoApplication;
import com.example.persistence.model.PUserAccount;
import com.example.persistence.model.PUserData;
import com.example.persistence.repo.PUserAccountRepository;
import com.example.service.BeanConfig;
import com.example.service.account.IAccountService;
import com.example.service.email.TrashMailException;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {BeanConfig.class, DemoApplication.class})
@EnableAutoConfiguration
public class AccountTests {

	private static final String TEST_EMAIL = "user@email.com";

	@Autowired
	private PUserAccountRepository pUserAccountRepository;

	@Autowired
	private IAccountService accountService;

	@Ignore
	@Test
	public void createAccount() {
		final PUserData pUserData = new PUserData();
		pUserData.setFirstName("fName");
		pUserData.setLastName("lName");
		final PUserAccount pUserAccount = new PUserAccount();
		pUserAccount.setEmail(TEST_EMAIL);
		pUserAccount.setUserData(pUserData);
		final PUserAccount pUserAccountSaved = pUserAccountRepository.save(pUserAccount);
		assertNotNull(pUserAccountSaved);
		assertNotNull(pUserAccountSaved.getId());
		System.out.println("save useraccount done");
	}

	@Ignore
	@Test
	public void findByEmail() {
		createAccount();
		final PUserAccount pUserAccount = pUserAccountRepository.findByEmail(TEST_EMAIL);
		assertNotNull(pUserAccount);
		
		final PUserData pUserData = pUserAccount.getUserData();
		assertNotNull(pUserData);
		assertEquals("fName", pUserData.getFirstName());

		System.out.println("useraccount found");
	}

	@Test
	public void accountService() {
		try {
			accountService.register(TEST_EMAIL, "aPassword", true);
		} catch (final TrashMailException tme) {
			tme.printStackTrace();
		}
		final PUserAccount pUserAccount = pUserAccountRepository.findByEmail(TEST_EMAIL);
		assertNotNull(pUserAccount);
	}
}
