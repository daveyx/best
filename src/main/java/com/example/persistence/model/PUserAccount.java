package com.example.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class PUserAccount extends PAbstractEntity {
	private String email;
	private String password;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userData_id")
	private PUserData userData;

	public PUserAccount() {
		super();
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%s, email='%s']", id, email);
	}
}
