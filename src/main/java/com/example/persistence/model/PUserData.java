package com.example.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "USER_DATA")
public class PUserData extends PAbstractEntity {

    @Column(name = "FIRSTNAME", unique = false, nullable = true, length = 50)
	private String firstName;

    @Column(name = "LASTNAME", unique = false, nullable = true, length = 50)
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

	@Override
	public String toString() {
		return String.format("PUserData[id=%s, firstName='%s']", id, firstName);
	}
}
