package com.moveo.epicure.aws;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    private MailSender mailSender;
    private static String systemMail = "gayay@academy-skills.com";
    private static String adminMail = "gayay@academy-skills.com";

    public EmailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void messageAdmin(String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(systemMail);
        simpleMailMessage.setTo(adminMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        mailSender.send(simpleMailMessage);
    }
}
