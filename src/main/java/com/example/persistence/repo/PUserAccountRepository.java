package com.example.persistence.repo;

import org.springframework.data.repository.CrudRepository;

import com.example.persistence.model.PUserAccount;

public interface PUserAccountRepository extends CrudRepository<PUserAccount, Long> {

	public PUserAccount findByEmail(final String email);
	public PUserAccount findByUuid(final String uuid);
}
