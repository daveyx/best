package com.example.t5.pages;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;

import com.example.elasticsearch.model.TestData;
import com.example.t5.services.IT5TestDataService;

public class BombServer {

	@Inject
	private IT5TestDataService t5TestDataService;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Inject
	private Block block;

	@Inject
	private Block block2;

	private Zone middlezone;

	@Property
	private TestData testData;

	void onActivate() {
		if (testData == null) {
			testData = t5TestDataService.getTestDataByUuid(TestData.TEST_UUID);
		}
	}

	void afterRender() {
		javaScriptSupport.require("serverbomber").invoke("init");

	}

	void onAjax() {
		ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
			public void run(JavaScriptSupport javascriptSupport) {
				javascriptSupport.require("serverbomber").invoke("bombserver1");
			}
		});

		ajaxResponseRenderer.addRender("middlezone", block);
	}

	void onAjax2() {
		ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
			public void run(JavaScriptSupport javascriptSupport) {
				javascriptSupport.require("serverbomber").invoke("bombserver2");
			}
		});
		ajaxResponseRenderer.addRender("middlezone", block2);
	}

	Object onAjax3(@RequestParameter("value") final String parm, @RequestParameter(value="uuid", allowBlank=true) final String uuidParm) {
		System.out.println("received an attack with parm=" + parm);
		final String uuid;
		if (StringUtils.isBlank(uuidParm)) {
			uuid = TestData.TEST_UUID;
		} else {
			uuid = uuidParm;
		}
		final TestData testData = t5TestDataService.getTestDataByUuid(uuid);
		testData.setValue(parm);
		t5TestDataService.saveTestDataByUuid(testData);
		final TestData testDataUpdated = t5TestDataService.getTestDataByUuid(uuid);
		final JSONObject json = new JSONObject();
		json.put("value", testDataUpdated.getValue());
//		try {
//			Thread.sleep(100);
//		} catch (final Exception e) {
//			e.printStackTrace();
//		}
		return new TextStreamResponse("application/json", json.toString());
	}
}
