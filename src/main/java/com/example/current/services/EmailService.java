package com.example.current.services;

import com.example.current.exceptions.EmailFailureException;
import com.example.current.model.LocalUser;
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
    private String from;
    @Value("${app.frontend.emailVerifyUrl}")
    private String emailVerifyUrl;
    @Value("${app.frontend.passwordResetUrl}")
    private String passwordResetUrl;

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
                emailVerifyUrl + "?token=" + token.getToken());
        try {
            mailSender.send(mail);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }

    public void sendPasswordResetEmail(LocalUser user, String token) throws EmailFailureException {
        var mail = makeMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Reset your password");
        mail.setText("Please follow the link below to reset your password.\n" +
                passwordResetUrl + "?token=" + token);
        try {
            mailSender.send(mail);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }
}
