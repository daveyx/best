package com.example.t5.admin.pages;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import com.example.elasticsearch.model.Article;
import com.example.service.IAdminElasticsearchService;
import com.example.t5.admin.components.AdminLayout;
import com.example.util.Tuple2;

@RequiresAuthentication
public class AdminElasticsearch {

	// -----------> services

	@Inject
	private AlertManager alertManager;

	@Inject
	private Request request;

	@Inject
	private IAdminElasticsearchService adminElasticsearchService;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	// -----------> components

	@Component(id = "template", parameters = {})
	private AdminLayout layoutComponent;

	@Inject
	private Block block;

	// -----------> properties

	@Property
	private String data;

	// -----------> events

	void onReindex() {
		final Tuple2<Integer, Integer> articleCount = adminElasticsearchService.reindexArticles();
		alertManager.success("Reindex Elasticsearch finished; articlecount=" + articleCount.getA() + "; cached-articlecount=" + articleCount.getB());
	}

	void onListarticles() {
		if (request.isXHR()) {
			final Iterable<Article> articles = adminElasticsearchService.getAllArticles();
			data = "";
			for (final Article article : articles) {
				data += article.toString() + "<br />";
			}
			if (StringUtils.isBlank(data)) {
				data = "no data available";
			}
			ajaxResponseRenderer.addRender("middlezone", block);
		}
	}
}
