package com.example.email;

import javax.mail.MessagingException;

import org.apache.commons.mail.EmailException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.service.email.EmailConfiguration;
import com.example.service.email.IMailSendService;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:email.properties")
@ContextConfiguration(classes = EmailConfiguration.class)
public class EmailTests {

	@Autowired
	private IMailSendService mailSendService;

	@Before
	public void before() {
	}

	@Test
	public void create() throws EmailException, MessagingException {
		final String subject = "a test email";
		final String bodyAsString = "<body><h1>A test e-mail...</h1></body>";
		final String recipient = "";
		mailSendService.sendHtmlMail(subject, bodyAsString, recipient);
		System.out.println("mail sent, look into your inbox...");
	}
}
