package com.fixmystreet.fixmystreet.services.impl;

import com.fixmystreet.fixmystreet.model.Token;
import com.fixmystreet.fixmystreet.model.User;
import com.fixmystreet.fixmystreet.model.enums.EmailVerificationToken;
import com.fixmystreet.fixmystreet.model.enums.Status;
import com.fixmystreet.fixmystreet.repository.EmailVerificationTokenRepository;
import com.fixmystreet.fixmystreet.services.EmailVerificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailVerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    public EmailVerificationServiceImpl(EmailVerificationTokenRepository tokenRepository, JavaMailSender mailSender) {
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
    }


    @Override
    public EmailVerificationToken createToken(User user) {

        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now().plusSeconds(24 * 3600)); // 24h expiry
        return tokenRepository.save(verificationToken);
    }

    @Override
    @Async
    public void sendVerificationEmail(User user, String appUrl) {
        EmailVerificationToken token = createToken(user);

        String verificationLink = appUrl + "/api/v1/auth/verify-email?token=" + token.getToken();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Email Verification");
        email.setText("Hi " + user.getName() + ",\n\nPlease verify your email by clicking the link: " + verificationLink);
        mailSender.send(email);

    }

    public void verifyToken(String token) {
        EmailVerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        if (verificationToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Token has expired");
        }

        User user = verificationToken.getUser();
        user.setStatus(Status.ACTIVE);
        tokenRepository.delete(verificationToken);
    }


}