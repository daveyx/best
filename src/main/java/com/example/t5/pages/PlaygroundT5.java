package com.example.t5.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import com.example.elasticsearch.model.TestData;
import com.example.t5.services.IT5TestDataService;

@RequiresAuthentication
public class PlaygroundT5 {

	@Inject
	private IT5TestDataService t5TestDataService;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private Block block;

	@Property
	private TestData testData;

	void onActivate() {
		if (testData == null) {
			testData = t5TestDataService.getTestDataByUuid(TestData.TEST_UUID);
		}
	}

	void onAjax() {
		if (testData != null) {
			testData.setValue("" + System.currentTimeMillis());
			t5TestDataService.saveTestDataByUuid(testData);
		}
		
		ajaxResponseRenderer.addRender("middlezone", block);
	}
}
