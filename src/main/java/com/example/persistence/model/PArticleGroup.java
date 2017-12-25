package com.example.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "ARTICLE_GROUP")
public class PArticleGroup extends PAbstractEntity {

	@Id
	@Column(name = "ARTICLE_GROUP_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "article_group_seq_gen")
	@SequenceGenerator(name = "article_group_seq_gen", sequenceName = "ARTICLE_GROUP_SEQ", allocationSize = 1)
	private Long id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "articleGroup", orphanRemoval = true)
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
	private List<PArticle> articles = new ArrayList<PArticle>();

    @Column(name = "NAME", unique = true, nullable = true, length = 50)
	private String name;

	@Override
	public String toString() {
		return String.format("PArticleGroup[id=%s, name='%s']", id, name);
	}
}
