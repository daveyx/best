package com.example.persistence.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "USER_ACCOUNT")
public class PUserAccount extends PAbstractEntity {

	@Column(name = "EMAIL", unique = true, nullable = false, length = 50)
	@NotNull
	private String email;

	@Column(name = "PASSWORD", unique = false, nullable = true)
	private String password;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_DATA_ID")
	private PUserData userData;

	@Column(name = "DATE_CREATION", nullable = false)
	@NotNull
	private Date dateCreation = new Date();

	public PUserAccount() {
		super();
	}

	public DateTime getDateCreation() {
		return (null == dateCreation) ? null : new DateTime(dateCreation.getTime());
	}

	public void setDateCreation(final DateTime dateCreation) {
		this.dateCreation = (null == dateCreation) ? null : new Date(dateCreation.getMillis());
	}

	@Override
	public String toString() {
		return String.format("PUserAccount[id=%s, email='%s']", id, email);
	}
}
