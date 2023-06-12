package com.example.Dawlati.Services;

import com.example.Dawlati.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class EmailSenderServiceTest {
    @Mock private JavaMailSender mailSender;
    @InjectMocks
    private EmailSenderService emailSenderService;

    @Test
    void sendEmailShouldInvokeSend() {
        String to = "jakedoe@gmail.com";
        String subject = "Subject";
        String text = "text";
        emailSenderService.sendEmail(to, subject, text);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dawlatiportal@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        verify(mailSender).send(message);
    }
}
