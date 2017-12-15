package com.example.t5.pages;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import com.example.elasticsearch.model.TestData;
import com.example.t5.services.IT5TestDataService;

public class AjaxForm {

	@Property
	@NotNull
	private String data;

	@Property
	private TestData testData;

	@Inject
	private Request request;

	@InjectComponent("ajaxForm")
	private Form form;

	@InjectComponent("data")
	private TextField dataField;

	@InjectComponent
	private Zone formZone;

	@Inject
	private IT5TestDataService t5TestDataService;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	void onActivate() {
		if (testData == null) {
			testData = t5TestDataService.getTestDataByUuid(TestData.TEST_UUID);
			data = testData.getValue();
		}
	}

	void onValidateFromAjaxForm() {
		if (StringUtils.isBlank(data)) {
			form.recordError(dataField, "A value must be provided");
		}
	}

	void onSuccess() {
		if (request.isXHR()) {
			testData.setValue(data);
			t5TestDataService.saveTestDataByUuid(testData);
			ajaxResponseRenderer.addRender(formZone);
		}
	}

	void onFailure() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(formZone);
		}
	}
}
