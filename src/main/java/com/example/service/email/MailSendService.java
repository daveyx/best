package com.example.service.email;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:email.properties")
@Component
public class MailSendService implements IMailSendService {

	private static final Log LOGGER = LogFactory.getLog(MailSendService.class);

	private final String smtpHost;
	private final int smtpPort;
	private final String smtpUser;
	private final String smtpPasswd;
	private final String fromEmail;
	private final String fromName;
	// private final String logoFile;
	// private final String logoName;

	public MailSendService(@Value("${email.host}") String smtpHost, @Value("${email.port}") String smtpPort,
			@Value("${email.user}") String smtpUser, @Value("${email.password}") String smtpPasswd,
			@Value("${email.fromEmail}") String fromEmail, @Value("${email.fromName}") String fromName) {

		if (StringUtils.isBlank(smtpHost)) {
			throw new IllegalStateException("MAILSMTPHOST not set");
		}

		if (StringUtils.isBlank(smtpPort)) {
			throw new IllegalStateException("MAILSMTPPORT not set");
		}

		if (StringUtils.isBlank(smtpUser)) {
			throw new IllegalStateException("MAILSMTPUSER not set");
		}

		if (StringUtils.isBlank(smtpPasswd)) {
			throw new IllegalStateException("MAILSMTPPASS not set");
		}

		if (StringUtils.isBlank(fromEmail)) {
			throw new IllegalStateException("MAILFROMEMAIL not set");
		}

		if (StringUtils.isBlank(fromName)) {
			throw new IllegalStateException("MAILFROMNAME not set");
		}

		// if (StringUtils.isBlank(logoFile)) {
		// throw new IllegalStateException("MAILLOGOFILE not set");
		// }
		//
		// if (StringUtils.isBlank(logoName)) {
		// throw new IllegalStateException("MAILLOGONAME not set");
		// }

		this.smtpHost = smtpHost;
		this.smtpPort = Integer.parseInt(smtpPort);
		this.smtpUser = smtpUser;
		this.smtpPasswd = smtpPasswd;
		this.fromEmail = fromEmail;
		this.fromName = fromName;
	}

	@Override
	public synchronized void sendHtmlMail(final String subject, final String bodyAsString, final String recipient)
			throws EmailException, MessagingException {
		final HtmlEmail email = new HtmlEmail();
		email.setSmtpPort(smtpPort);
		email.setHostName(smtpHost);
		email.setAuthenticator(new DefaultAuthenticator(smtpUser, smtpPasswd));
		email.setSSLOnConnect(true);
		email.addTo(recipient);
		email.setFrom(fromEmail, fromName);
		email.setSubject(subject);

		// final ClassLoader cl = getClass().getClassLoader();
		// final URL url = cl.getResource(logoFile);
		// final String cid = email.embed(url, logoName);
		//
		// final String bodyWithEmbeddedImage = StringUtils.replace(bodyAsString, "<img
		// src=\"logo.jpg\"", "<img src=\"cid:" + cid + "\"");
		// email.setHtmlMsg(bodyWithEmbeddedImage);
		// email.setTextMsg("Your email client does not support HTML messages");
		email.setHtmlMsg(bodyAsString);
		email.setTextMsg("Your email client does not support HTML messages");

		email.send();

		LOGGER.info("sent mail subject [" + subject + "] recipient [" + recipient + "]");
	}

}
