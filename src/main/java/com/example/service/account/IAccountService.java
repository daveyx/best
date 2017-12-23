package com.example.service.account;

public interface IAccountService {

	void register(final String email, final String plainTextPassword, final boolean newsletterAccepted);
}
