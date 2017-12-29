package com.example.service.email;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.example.persistence.model.PEmailEvent;
import com.example.persistence.model.PUserAccount;
import com.example.persistence.repo.PEmailEventRepository;

@Component
@PropertySource("classpath:email.properties")
public class MailContentCreationService implements IMailContentCreationService {

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private PEmailEventRepository pEmailEventRepository;

	@Value("${com.example.accountackurl}")
	private String accountAckUrl;

	@Override
	public String createTestMail() {
		final Template vcTemplate = velocityEngine.getTemplate("velocity/testMail.vm");

		final VelocityContext vcContext = new VelocityContext();
		vcContext.put("pParm", "a parameter from the backend");

		final StringWriter out = new StringWriter();
		vcTemplate.merge(vcContext, out);
		return out.toString();
	}

	@Override
	public void createRegistrationConfirmationMail(final Long accountId, final String emailAdress, final String ackToken) {
		final Template vcTemplate = velocityEngine.getTemplate("velocity/registrationConfirmationMail.vm");

		final VelocityContext vcContext = new VelocityContext();
		vcContext.put("pAckLink", accountAckUrl + ackToken);

		final StringWriter out = new StringWriter();
		vcTemplate.merge(vcContext, out);
		
		final PEmailEvent pEmailEvent = new PEmailEvent();
		final PUserAccount fakeAccount = new PUserAccount();
		fakeAccount.setId(accountId);
		pEmailEvent.setAccount(fakeAccount);
		pEmailEvent.setRecipientEmail(emailAdress);
		
		pEmailEventRepository.save(pEmailEvent);
	}
}
