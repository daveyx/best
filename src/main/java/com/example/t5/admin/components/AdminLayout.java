package com.example.t5.admin.components;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.Import;

@RequiresAuthentication
@RequiresRoles("admin")
@Import(stylesheet="context:static/css/layout.css")
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
