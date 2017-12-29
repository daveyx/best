package com.example.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "EMAIL_EVENT")
public class PEmailEvent extends PAbstractEntity {

	@Id
	@Column(name = "EMAIL_EVENT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "emaileventseqgen")
	@SequenceGenerator(name = "emaileventseqgen", sequenceName = "EMAIL_EVENT_SEQ")
	private Long id;

	@Column(name = "DATE_MODIFIED", unique = false, nullable = false)
	private Date dateModified = new Date();

	@Column(name = "DATE_PROCESSED", unique = false, nullable = true)
	private Date dateProcessed = null;

	@Column(name = "DATE_IN_PROGRESS", unique = false, nullable = true)
	private Date dateInProgress = null;

	@Column(name = "RECIPIENT_EMAIL", unique = false, nullable = false, length = 50)
	@NotNull
	@Length(min = 2)
	private String recipientEmail;

	@Column(name = "SUBJECT", unique = false, nullable = false, length = 100)
	@NotNull
	@Length(min = 2)
	private String subject;

	@Column(name = "BODY_HTML", unique = false, nullable = false)
	@NotNull
	@Type(type = "text")
	private String bodyHtml;

	@Column(name = "ATTACHMENT_PATH", unique = false, nullable = true, length = 256)
	@Length(min = 2)
	private String attachmentPath;

	@ManyToOne
	@JoinColumn(name = "FK_ACCOUNT_ID", nullable = true)
	private PUserAccount account = null;

	public DateTime getDateModified() {
		return (null == dateModified) ? null : new DateTime(dateModified.getTime());
	}

	public void setDateModified(final DateTime dateModified) {
		this.dateModified = new Date(dateModified.getMillis());
	}

	public DateTime getDateProcessed() {
		return (null == dateProcessed) ? null : new DateTime(dateProcessed.getTime());
	}

	public void setDateProcessed(final DateTime dateProcessed) {
		this.dateProcessed = new Date(dateProcessed.getMillis());
		updateModified();
	}

	public DateTime getDateInProgress() {
		return (null == dateInProgress) ? null : new DateTime(dateInProgress.getTime());
	}

	public void setDateInProgress(final DateTime dateInProgress) {
		this.dateInProgress = new Date(dateInProgress.getMillis());
		updateModified();
	}


	//
	// ---->>private
	//

	private void updateModified() {
		this.dateModified = new Date();
	}
}
