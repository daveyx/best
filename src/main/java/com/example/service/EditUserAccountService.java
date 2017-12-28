package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.persistence.model.PUserAccount;
import com.example.persistence.model.PUserData;
import com.example.persistence.repo.PUserAccountRepository;
import com.example.persistence.repo.PUserDataRepository;
import com.example.util.Tuple2;

public class EditUserAccountService implements IEditUserAccountService {

	@Autowired
	private PUserAccountRepository pUserAccountRepository;

	@Autowired
	private PUserDataRepository pUserDataRepository;

	@Override
	public Tuple2<PUserAccount, PUserData> getAccountByUuid(final String uuid) {
		final PUserAccount pUserAccount = pUserAccountRepository.findByUuid(uuid);
		final PUserData pUserData;
		if (pUserAccount != null) {
			pUserData = pUserDataRepository.findById(pUserAccount.getUserData().getId()).get();
			return new Tuple2<PUserAccount, PUserData>(pUserAccount, pUserData);
		} else {
			return null;
		}
	}

	@Override
	public void update(final PUserAccount pUserAccount, final PUserData pUserData) {
		pUserAccount.setUserData(pUserData);
		pUserAccountRepository.save(pUserAccount);
	}
}
