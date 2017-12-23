package com.example.persistence.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class PAbstractEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	protected Long id;

	@Column(name = "UUID", unique = true, nullable = true, length = 50)
	protected String uuid;

	public PAbstractEntity() {
		uuid = UUID.randomUUID().toString();
	}
}

