package com.example.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

    @Column(name = "HEADING", unique = true, nullable = true, length = 50)
	private String heading;

    @Column(name = "INTRO", unique = false, nullable = true, length = 2048)
	private String intro;

    @Column(name = "IMAGE", unique = false, nullable = true, length = 512)
	private String image;

}
