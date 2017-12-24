package com.example.t5.admin.components;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

@RequiresAuthentication
public class AdminLayout {

	// -----------> services
	// -----------> components
	// -----------> properties

	// -----------> events

	public String getTitle() {
		return "Admin";
	}

	public boolean isDevEnv() {
		final String t5execMode = System.getProperty("tapestry.execution-mode");
		if (StringUtils.isBlank(t5execMode)) {
			return false;
		}
		if ("Development".equalsIgnoreCase(t5execMode)) {
			return true;
		}
		return false;
	}

	public boolean isQAEnv() {
		final String t5execMode = System.getProperty("tapestry.execution-mode");
		if (StringUtils.isBlank(t5execMode)) {
			return false;
		}
		if ("QA".equalsIgnoreCase(t5execMode)) {
			return true;
		}
		return false;
	}
}
