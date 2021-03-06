package com.example.service.account;

import com.example.service.email.TrashMailException;

public interface IAccountService {

	void register(final String email, final String plainTextPassword, final boolean newsletterAccepted,
			final boolean sendAckMail) throws TrashMailException;

	boolean isEmailExisting(final String email);

	String getAccountAcknowledgeLink(final String url, final String uuid);

	String getUuidFromAccountAcknowledgeLink(final String encoded);
}
