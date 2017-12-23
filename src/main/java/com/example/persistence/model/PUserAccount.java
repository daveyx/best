package com.example.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "USER_ACCOUNT")
public class PUserAccount extends PAbstractEntity {

	@Column(name = "EMAIL", unique = true, nullable = false, length = 50)
    @NotNull
	private String email;

	@Column(name = "PASSWORD", unique = false, nullable = true)
	private String password;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userAccount")
    @JoinColumn(name = "USER_DATA_ID")
	@MapsId
	private PUserData userData;

	public PUserAccount() {
		super();
	}

	@Override
	public String toString() {
		return String.format("PUserAccount[id=%s, email='%s']", id, email);
	}
}
