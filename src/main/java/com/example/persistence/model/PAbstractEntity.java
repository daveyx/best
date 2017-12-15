package com.example.persistence.model;

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
	protected Long id;

	protected String uuid;
}

