package com.example.service.email;

public interface IMailContentCreationService {
	String createTestMail();

	void createRegistrationConfirmationMail(final Long accountId, final String email, final String ackToken);
}
