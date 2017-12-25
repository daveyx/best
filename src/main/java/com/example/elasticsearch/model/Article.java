package com.example.elasticsearch.model;

import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(indexName = "data", type = "article")
public class Article extends AbstractEntity {

	private String articleGroup;
	private String heading;
	private String intro;
	private String image;
	private Date datePublished;

	@Override
	public String toString() {
		return String.format("Article[heading='%s']", heading);
	}
}
