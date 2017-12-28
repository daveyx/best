package com.example.service;

import com.example.persistence.model.PUserAccount;
import com.example.persistence.model.PUserData;
import com.example.util.Tuple2;

public interface IEditUserAccountService {

	Tuple2<PUserAccount, PUserData> getAccountByUuid(final String uuid);

	void update(final PUserAccount pUserAccount, final PUserData pUserData);

}
