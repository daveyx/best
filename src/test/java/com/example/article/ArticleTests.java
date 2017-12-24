package com.example.article;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.DemoApplication;
import com.example.persistence.model.PArticle;
import com.example.persistence.model.PArticleGroup;
import com.example.service.BeanConfig;
import com.example.service.IArticleAccessService;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {BeanConfig.class, DemoApplication.class})
public class ArticleTests {

	@Autowired
	private IArticleAccessService articleAccessService;

	@Ignore
	@Test
	public void createArticleGroup() {
		articleAccessService.createArticleGroup("articleGroup1");
		final PArticleGroup pArticleGroup = articleAccessService.getArticleGroupByName("articleGroup1");
		assertNotNull(pArticleGroup);
		System.out.println("articlegroup created");
	}

	@Test
	public void createArticle() {
		createArticleGroup();
		final PArticleGroup pArticleGroup = articleAccessService.getArticleGroupByName("articleGroup1");
		assertNotNull(pArticleGroup);
		articleAccessService.createArticle(pArticleGroup, "A new article");
		
		final PArticle pArticle = articleAccessService.getArticleByHeading("A new article");
		assertNotNull(pArticle);
		System.out.println("article created");
	}

}
