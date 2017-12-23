package com.example.service.account;

import org.apache.shiro.authc.credential.PasswordService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.persistence.model.PUserAccount;
import com.example.persistence.repo.PUserAccountRepository;

public class AccountService implements IAccountService {

	private static Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private PUserAccountRepository pUserAccountRepository;

	@Autowired
	private PasswordService passwordService;

	@Override
	public void register(final String email, final String plainTextPassword, final boolean newsletterAccepted)
	{
		final PUserAccount pAccount = new PUserAccount();
		pAccount.setEmail(email);
		pAccount.setDateCreation(new DateTime());
//		if (newsletterAccepted) {
//			pAccount.setNewsLetter("1");
//		}

		pAccount.setPassword(passwordService.encryptPassword(plainTextPassword));
		pUserAccountRepository.save(pAccount);
		
		LOGGER.info("User registered with email=" + pAccount.getEmail() + " and hashedPassword=" + pAccount.getPassword());
	}
}
