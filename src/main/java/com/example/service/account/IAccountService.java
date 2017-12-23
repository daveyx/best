package com.example.service.account;

import com.example.service.email.TrashMailException;

public interface IAccountService {

	void register(final String email, final String plainTextPassword, final boolean newsletterAccepted) throws TrashMailException;

	boolean isEmailExisting(final String email);
}
