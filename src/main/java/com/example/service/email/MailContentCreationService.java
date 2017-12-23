package com.example.service.email;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailContentCreationService implements IMailContentCreationService {

	@Autowired
	private VelocityEngine velocityEngine;
	
	public String createTestMail()
	{
		final Template vcTemplate = velocityEngine.getTemplate("velocity/testMail.vm");
		
		final VelocityContext vcContext = new VelocityContext();
		vcContext.put("pParm", "a parameter from the backend");
	
		final StringWriter out = new StringWriter();
		vcTemplate.merge(vcContext, out);
		return out.toString();
	}
}
