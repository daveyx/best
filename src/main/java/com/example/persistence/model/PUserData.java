package com.example.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "USER_DATA")
public class PUserData extends PAbstractEntity {

	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_data_seq_gen")
    @SequenceGenerator(name = "user_data_seq_gen", sequenceName = "USER_DATA_SEQ")
	private Long id;

    @Column(name = "NICKNAME", unique = true, nullable = true, length = 50)
	private String nickName;

    @Column(name = "FIRSTNAME", unique = false, nullable = true, length = 50)
	private String firstName;

    @Column(name = "LASTNAME", unique = false, nullable = true, length = 50)
	private String lastName;

    @Column(name = "USERIMAGE", unique = false, nullable = true, length = 512)
	private String userImage;

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
