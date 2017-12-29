package com.example.service.account;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.example.persistence.model.PUserAccount;
import com.example.persistence.repo.PUserAccountRepository;
import com.example.service.email.IMailContentCreationService;
import com.example.service.email.TrashMail;
import com.example.service.email.TrashMailException;
import com.example.util.MapEncoderDecoder;

public class AccountService implements IAccountService {

	private static Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private PUserAccountRepository pUserAccountRepository;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private IMailContentCreationService mailContentCreationService;

	private final MapEncoderDecoder mapEncoderDecoder;

	public AccountService() {
		mapEncoderDecoder = new MapEncoderDecoder(
				com.example.service.account.AccountService.class.getName().toCharArray());
	}

	@Override
	public void register(final String email, final String plainTextPassword, final boolean newsletterAccepted, final boolean sendAckMail)
			throws TrashMailException {
		for (final String trashMail : TrashMail.TRASHMAILS) {
			if (email.contains(trashMail)) {
				throw new TrashMailException("Trashmail " + trashMail + " detected, aborting");
			}
		}
		final PUserAccount pAccount = new PUserAccount();
		pAccount.setEmail(email);
		pAccount.setDateCreation(new DateTime());
		// if (newsletterAccepted) {
		// pAccount.setNewsLetter("1");
		// }

		pAccount.setPassword(passwordService.encryptPassword(plainTextPassword));
		final PUserAccount pUserAccountSaved = pUserAccountRepository.save(pAccount);
		
		if (sendAckMail) {
			createRegistrationConfirmationMail(pUserAccountSaved.getId(), pUserAccountSaved.getEmail(), pUserAccountSaved.getUuid());
		}
		
		LOGGER.info(
				"User registered with email=" + pAccount.getEmail() + " and hashedPassword=" + pAccount.getPassword());
	}

	@Override
	public boolean isEmailExisting(final String email) {
		final PUserAccount pUserAccount = pUserAccountRepository.findByEmail(email);
		if (pUserAccount == null) {
			return false;
		}
		return true;
	}

	@Override
	public String getAccountAcknowledgeLink(final String url, final String uuid) {
		if (StringUtils.isNotBlank(url)) {
			return url + "/" + encode(uuid);
		}
		return encode(uuid);
	}

	@Override
	public String getUuidFromAccountAcknowledgeLink(final String encoded) {
		return decode(encoded);
	}

	//
	// ---> private
	//

	@Async
	private void createRegistrationConfirmationMail(final Long accountId, final String email, final String uuid) {
		mailContentCreationService.createRegistrationConfirmationMail(accountId, email, encode(uuid));
	}

	private String encode(final String uuid) {
		final Map<String, String> map = new HashMap<String, String>();

		map.put("T", "AA"); // (T)opic = account acknowledge
		map.put("U", uuid); // (U)uid
		map.put("R", Integer.toString(new Random().nextInt())); // (R)andom = random

		try {
			return Base64.encodeBase64URLSafeString(mapEncoderDecoder.encryptMap(map).getBytes("UTF-8"));
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalStateException("this should never happen", e);
		}
	}

	private String decode(final String accountAcknowledgeLink) {
		if (null != accountAcknowledgeLink) {
			try {
				final String decodedLink = new String(Base64.decodeBase64(accountAcknowledgeLink.getBytes("UTF-8")),
						"UTF-8");

				final Map<String, String> map = mapEncoderDecoder.decryptMap(decodedLink);

				final String t = map.get("T"); // (T)opic = account acknowledge
				if ("AA".equals(t)) {
					return map.get("U");
				}
			} catch (final UnsupportedEncodingException e) {
				throw new IllegalStateException("this should never happen", e);
			} catch (final Exception e) {
				// do not throw any illegal block size exception
			}
		}

		return null;
	}
}
