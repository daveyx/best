package com.example.t5.pages;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
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
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import com.example.service.account.IAccountService;

public class Register {

	// -----------> services

	@Inject
	private IAccountService accountService;

	@Inject
	private SecurityService securityService;

	@Inject
	private AlertManager alertManager;

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
			registrationForm.recordError(registerPasswordField, "Fehler - bitte keine Sonderzeichen benutzen.");
			alertManager.error(
					"Für das Passwort sind nur die Zeichen A-Z a-z und 0-9 erlaubt! Bitte keine Sonderzeichen benutzen.");
		}

		if (accountService.isEmailExisting(registerEmail)) {
			registrationForm.recordError(registerEmailField, "Fehler - bitte eine andere E-Mail Adresse benutzen.");
			alertManager.error("Diese E-Mail Adresse ist nicht erlaubt. Btte eine andere E-Mail Adresse benutzen.");
		}
	}

	Object onSuccessFromRegistrationForm() {
		accountService.register(registerEmail, registerPassword, registerNewsLetter);
		final String loginMessage = doLogin(registerEmail, registerPassword);
		if (StringUtils.isNotBlank(loginMessage)) {
			alertManager.error(loginMessage);
			return null;
		} else {
			alertManager.success("Bitte bestätigen Sie die Registrierung durch Klick auf den Link in der E-Mail welche wir Ihnen zugesendet haben.");
			return Index.class;
		}
	}
	
	
	//
	// ---> private
	//
	
	
	private String doLogin(final String email, final String password)
	{
		String loginErrorMessage = null;

		final Subject currentUser = securityService.getSubject();
		if (currentUser == null) {
			return "fatal error: Subject is null";
		}

		final UsernamePasswordToken token = new UsernamePasswordToken(email, password);
		try {
			currentUser.login(token);
//		}
//		catch (final UnknownAccountException e) {
//			if (_registrationService.isAccountNotAcknowledged(email)){
//				throw new AccountNotAcknowledgedException();
//			} else {
//				loginErrorMessage = "Dieses Konto existiert nicht.";
//			}
		} catch (IncorrectCredentialsException e) {
			loginErrorMessage = "Falsches Passwort.";
		} catch (LockedAccountException e) {
			loginErrorMessage = "Dieses Konto ist inaktiv. Bitte kontaktieren Sie uns.";
		} catch (AuthenticationException e) {
			loginErrorMessage = "AuthenticationError";
		}
		
		return loginErrorMessage;
	}

}
