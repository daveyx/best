package com.example.service.email;

import javax.mail.MessagingException;

import org.apache.commons.mail.EmailException;

public interface IMailSendService {

	void sendHtmlMail(final String subject, final String bodyAsString, final String recipient) throws EmailException, MessagingException;
}
