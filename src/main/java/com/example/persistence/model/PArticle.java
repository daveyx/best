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

import org.joda.time.DateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "ARTICLE")
public class PArticle extends PAbstractEntity {

	@Id
	@Column(name = "ARTICLE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "article_seq_gen")
	@SequenceGenerator(name = "article_seq_gen", sequenceName = "ARTICLE_SEQ", allocationSize = 1)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "FK_ARTICLEGROUP_ID", nullable = false)
	private PArticleGroup articleGroup;

    @Column(name = "AUTHOR", unique = false, nullable = true, length = 50)
	private String author;

    @Column(name = "HEADING", unique = true, nullable = true, length = 50)
	private String heading;

    @Column(name = "INTRO", unique = false, nullable = true, length = 2048)
	private String intro;

    @Column(name = "IMAGE", unique = false, nullable = true, length = 512)
	private String image;

	@Column(name = "DATE_PUBLISHED", nullable = true)
	private Date datePublished = null;

	@Column(name = "PUBLISHED", nullable = false)
	@NotNull
	private boolean published = false;

	public DateTime getDatePublished() {
		return (null == datePublished) ? null : new DateTime(datePublished.getTime());
	}

	public void setDatePublished(final DateTime datePublished) {
		this.datePublished = (null == datePublished) ? null : new Date(datePublished.getMillis());
	}

	@Override
	public String toString() {
		return String.format("PArticle[id=%s, heading='%s']", id, heading);
	}
}
