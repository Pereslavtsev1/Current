package com.example.current.services;

import com.example.current.exceptions.EmailFailureException;
import com.example.current.model.VerificationJWTEmailToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EmailService {
    @Value("mail.from")
    private  String from;
    @Value("${app.frontend.url}")
    private String url;

    private final JavaMailSender mailSender;

    public SimpleMailMessage makeMailMessage() {
        var mail = new SimpleMailMessage();
        new SimpleMailMessage().setFrom(from);
        return mail;
    }
    public void sendVerificationMail(VerificationJWTEmailToken token) throws EmailFailureException {
        var mail = makeMailMessage();
        mail.setTo(token.getUser().getEmail());
        mail.setSubject("Verify your email address to activate your account");
        mail.setText("Please follow the link below to verify your email to active your account.\n" +
                url + "?token=" + token.getToken());
        try {
            mailSender.send(mail);
        } catch (MailException ex) {
            throw new EmailFailureException("");
        }
    }
}
