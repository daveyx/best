package com.example.t5.pages;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import com.example.service.account.IAccountService;
import com.example.service.email.TrashMailException;

public class Register {

	// -----------> services

	@Inject
	private IAccountService accountService;

	@Inject
	private SecurityService securityService;

	@Inject
	private AlertManager alertManager;

	@Inject
	private Messages messages;

	// -----------> components

	@InjectComponent
	private Form registrationForm;

	@Component(id = "registerEmail", parameters = "validate=required,email")
	private TextField registerEmailField;

	@Component(id = "registerPassword", parameters = "validate=required")
	private PasswordField registerPasswordField;

	// -----------> properties

	@Property
	private String registerEmail;

	@Property
	private String registerPassword;

	@Property
	private boolean registerNewsLetter;

	// -----------> events

	@SetupRender
	void setup() {
		registerNewsLetter = true;
	}

	void onValidateFromRegistrationForm() {

		if (StringUtils.isNotBlank(registerPassword) && !registerPassword.matches("^[A-Za-z0-9]+$")) {
			registrationForm.recordError(registerPasswordField,
					messages.get("com.example.registration.passwordformat1"));
			alertManager.error(messages.get("com.example.registration.passwordformat2"));
		}

		if (accountService.isEmailExisting(registerEmail)) {
			registrationForm.recordError(registerEmailField, messages.get("com.example.registration.emailnotalowed1"));
			alertManager.error(messages.get("com.example.registration.emailnotalowed2"));
		}
	}

	Object onSuccessFromRegistrationForm() {
		try {
			accountService.register(registerEmail, registerPassword, registerNewsLetter);
		} catch (final TrashMailException e) {
			e.printStackTrace();
			registrationForm.recordError(registerEmailField, messages.get("com.example.registration.emailnotalowed1"));
			alertManager.error(messages.get("com.example.registration.emailnotalowed2"));
			return null;
		}
		final String loginMessage = doLogin(registerEmail, registerPassword);
		if (StringUtils.isNotBlank(loginMessage)) {
			alertManager.error(loginMessage);
			return null;
		} else {
			alertManager.success("com.example.registration.success");
			return Index.class;
		}
	}

	//
	// ---> private
	//

	private String doLogin(final String email, final String password) {
		String loginErrorMessage = null;

		final Subject currentUser = securityService.getSubject();
		if (currentUser == null) {
			return "fatal error: Subject is null";
		}

		final UsernamePasswordToken token = new UsernamePasswordToken(email, password);
		try {
			currentUser.login(token);
			// }
			// catch (final UnknownAccountException e) {
			// if (_registrationService.isAccountNotAcknowledged(email)){
			// throw new AccountNotAcknowledgedException();
			// } else {
			// loginErrorMessage = "Dieses Konto existiert nicht.";
			// }
		} catch (final IncorrectCredentialsException e) {
			loginErrorMessage = messages.get("com.example.registration.IncorrectCredentialsException");
		} catch (final LockedAccountException e) {
			loginErrorMessage = messages.get("com.example.registration.LockedAccountException");
		} catch (final AuthenticationException e) {
			loginErrorMessage = messages.get("com.example.registration.AuthenticationException");
		}

		return loginErrorMessage;
	}

}
