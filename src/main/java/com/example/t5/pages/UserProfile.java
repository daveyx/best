package com.example.t5.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import com.example.persistence.model.PUserAccount;
import com.example.persistence.model.PUserData;
import com.example.service.IEditUserAccountService;
import com.example.util.Tuple2;

@RequiresAuthentication
public class UserProfile {

	// -----------> services

	@Inject
	private SecurityService securityService;

	@Inject
	private IEditUserAccountService editUserAccountService;

	@Inject
	private AlertManager alertManager;

	// -----------> components

	@InjectComponent("editform")
	private Form form;

	// -----------> properties

	@Property
	private PUserAccount pUserAccount;

	@Property
	private PUserData pUserData;

	// -----------> events

	@SetupRender
	void setup() {
		initAccount();
	}

	void onPrepareForSubmit() {
		initAccount();
	}

	void onSuccess() {
		editUserAccountService.update(pUserAccount, pUserData);
		alertManager.success("Useraccount updated");
	}
	
	//
	// ---> private
	//
	
	void initAccount() {
		final Subject subject = securityService.getSubject();
		final String uuidFromSecurityContext = (String) subject.getPrincipal();
		final Tuple2<PUserAccount, PUserData> accountData =  editUserAccountService.getAccountByUuid(uuidFromSecurityContext);
		pUserAccount = accountData.getA();
		pUserData = accountData.getB();
	}
}
