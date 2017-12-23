package com.example.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.persistence.model.PUserAccount;
import com.example.persistence.model.PUserData;
import com.example.persistence.repo.PUserAccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class AccountTests {

	@Autowired
	private PUserAccountRepository pUserAccountRepository;

	@Test
	public void createAccount() {
		final PUserData pUserData = new PUserData();
		pUserData.setFirstName("fName");
		pUserData.setLastName("lName");
		final PUserAccount pUserAccount = new PUserAccount();
		pUserAccount.setEmail("user@email.com");
		pUserAccount.setUserData(pUserData);
		final PUserAccount pUserAccountSaved = pUserAccountRepository.save(pUserAccount);
		assertNotNull(pUserAccountSaved);
		assertNotNull(pUserAccountSaved.getId());
		System.out.println("save useraccount done");
	}

	@Test
	public void findByEmail() {
		createAccount();
		final PUserAccount pUserAccount = pUserAccountRepository.findByEmail("user@email.com");
		assertNotNull(pUserAccount);
		
		final PUserData pUserData = pUserAccount.getUserData();
		assertNotNull(pUserData);
		assertEquals("fName", pUserData.getFirstName());

		System.out.println("useraccount found");
	}
}
