package com.example.email;

import javax.mail.MessagingException;

import org.apache.commons.mail.EmailException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.service.email.EmailConfiguration;
import com.example.service.email.IMailContentCreationService;
import com.example.service.email.IMailSendService;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:email.properties")
@ContextConfiguration(classes = EmailConfiguration.class)
public class EmailTests {

	@Autowired
	private Environment environment;

	@Autowired
	private IMailContentCreationService mailContentCreationService;

	@Autowired
	private IMailSendService mailSendService;

	@Before
	public void before() {
	}

	@Ignore
	@Test
	public void createVelocityMail() throws EmailException, MessagingException {
		final String subject = "a test email";
		final String bodyAsString = mailContentCreationService.createTestMail();
		final String recipient = environment.getProperty("email.user");
		mailSendService.sendHtmlMail(subject, bodyAsString, recipient);
		System.out.println("mail sent, look into your inbox...");
	}

	@Ignore
	@Test
	public void createMail() throws EmailException, MessagingException {
		final String subject = "a test email";
		final String bodyAsString = "<body><h1>A test e-mail...</h1></body>";
		final String recipient = environment.getProperty("email.user");
		mailSendService.sendHtmlMail(subject, bodyAsString, recipient);
		System.out.println("mail sent, look into your inbox...");
	}
}
