package com.example.persistence.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class PUserData extends PAbstractEntity {

	private String firstName;
	private String lastName;

	@OneToOne(mappedBy = "userData")
	private PUserAccount userAccount;

	public PUserData() {
		super();
	}

	public PUserData(final String firstName, final String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
