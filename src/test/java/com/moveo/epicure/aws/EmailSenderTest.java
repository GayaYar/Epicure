package com.moveo.epicure.aws;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@ExtendWith(MockitoExtension.class)
public class EmailSenderTest {
    private EmailSender emailSender;
    @Mock
    private MailSender mailSender;
    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageArgumentCaptor;


    @BeforeEach
    void initialiseTest() {
        emailSender = new EmailSender(mailSender);
    }

    @Test
    void messageAdminCorrectValues() {
        String subject = "the subject";
        String body = "the body";
        emailSender.messageAdmin(subject, body);
        Mockito.verify(mailSender, Mockito.times(1)).send(messageArgumentCaptor.capture());
        SimpleMailMessage value = messageArgumentCaptor.getValue();
        assertEquals(value.getFrom(), "gayay@academy-skills.com");
        assertEquals(value.getTo()[0], "gayay@academy-skills.com");
        assertEquals(value.getSubject(), subject);
        assertEquals(value.getText(), body);
    }
}