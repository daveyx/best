package com.example.persistence.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class PAbstractEntity {

	@Column(name = "UUID", unique = true, nullable = true, length = 50)
	protected String uuid;

	public PAbstractEntity() {
		uuid = UUID.randomUUID().toString();
	}
}

