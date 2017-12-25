package com.example.persistence.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class PAbstractEntity {

	@Column(name = "UUID", unique = true, nullable = true, length = 50)
	protected String uuid;

	@Column(name = "DATE_CREATION", nullable = false)
	@NotNull
	private Date dateCreation = new Date();

	@Column(name = "CREATED_BY", nullable = true)
	private Long createdBy = null;

	@Column(name = "UPDATE_CACHE", nullable = false)
	@NotNull
	private boolean updateCache = true;

	public PAbstractEntity() {
		uuid = UUID.randomUUID().toString();
	}

	public DateTime getDateCreation() {
		return (null == dateCreation) ? null : new DateTime(dateCreation.getTime());
	}

	public void setDateCreation(final DateTime dateCreation) {
		this.dateCreation = (null == dateCreation) ? null : new Date(dateCreation.getMillis());
	}
}

