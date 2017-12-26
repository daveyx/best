package com.example.t5.admin.pages;

import java.util.ArrayList;
import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.joda.time.DateTime;

import com.example.persistence.model.PArticle;
import com.example.service.IPArticleAccessService;
import com.example.t5.admin.components.AdminLayout;

@RequiresAuthentication
public class AdminArticle {

	private static final ArrayList<String> hourList = new ArrayList<String>() {
		private static final long serialVersionUID = 7478581347041841178L;
		{
			add("0");
			add("4");
			add("6");
			add("8");
			add("10");
			add("12");
			add("14");
			add("15");
			add("16");
			add("17");
			add("18");
			add("19");
			add("20");
			add("21");
			add("22");
			add("23");
		}
	};

	private static final ArrayList<String> minuteList = new ArrayList<String>() {
		private static final long serialVersionUID = 7478581347041841178L;
		{
			add("0");
			add("15");
			add("30");
			add("45");
		}
	};

	// -----------> services

	@Inject
	private IPArticleAccessService articleAccessService;

	@Inject
	private AlertManager alertManager;

	@Inject
	private SelectModelFactory selectModelFactory;

	// -----------> components

	@Component(id = "template", parameters = {})
	private AdminLayout layoutComponent;

	@InjectComponent("articleform")
	private Form form;

	@Component(id = "hourPublished", parameters = { "model=hourPublishedModel", "blankOption=never",
			"value=hourPublished" })
	private Select hourPublishedComponent;

	@Component(id = "minutePublished", parameters = { "model=minutePublishedModel", "blankOption=never",
			"value=minutePublished" })
	private Select minutePublishedComponent;

	// -----------> properties

	@Property
	private PArticle pArticle;

	@Property
	private long articleId;

	@Property
	private Date datePublished;

	@Property
	private SelectModel hourPublishedModel;

	@Property
	private int hourPublished;

	@Property
	private SelectModel minutePublishedModel;

	@Property
	private int minutePublished;

	// -----------> events

	void onActivate(final long articleId) {
		this.articleId = articleId;
	}

	void onPrepareForRender() {
		pArticle = articleAccessService.getArticleById(articleId);
		hourPublishedModel = selectModelFactory.create(hourList);
		minutePublishedModel = selectModelFactory.create(minuteList);
		final DateTime dtPublished = pArticle.getDatePublished();
		if (dtPublished != null) {
			minutePublished = dtPublished.getMinuteOfHour();
			hourPublished = dtPublished.getHourOfDay();
			datePublished = dtPublished.toDate();
		}
	}

	Object onPassivate() {
		return articleId;
	}

	void onPrepareForSubmit() {
		pArticle = articleAccessService.getArticleById(articleId);
	}

	void onValidateFromArticleForm() {
		// TODO: validate
	}

	void onSuccess() {
		if (datePublished != null) {
			final DateTime publishingDateTime = new DateTime(datePublished).withHourOfDay(hourPublished).withMinuteOfHour(minutePublished);
			pArticle.setDatePublished(publishingDateTime);
		} else {
			pArticle.setDatePublished(null);
		}
		articleAccessService.save(pArticle);
		alertManager.success("Successfully saved the article with id=" + articleId);
	}
}
