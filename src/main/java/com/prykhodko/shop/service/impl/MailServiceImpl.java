package com.prykhodko.shop.service.impl;

import com.prykhodko.shop.model.ConfirmationCode;
import com.prykhodko.shop.service.MailService;
import org.apache.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailServiceImpl implements MailService {

    Logger logger = Logger.getLogger(MailServiceImpl.class);

    @Override
    public void sendConfirmCode(ConfirmationCode code) {

        final String username = "forprojectemail0@gmail.com";
        final String password = "test@test";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(code.getUser().getEmail())
            );
            message.setSubject("Confirmation code");
            message.setText("Enter this code - [" + code.getCode() + "] for confirmation");

            Transport.send(message);
        } catch (MessagingException e) {
            logger.error("Send message was falling", e);
        }
    }
}
