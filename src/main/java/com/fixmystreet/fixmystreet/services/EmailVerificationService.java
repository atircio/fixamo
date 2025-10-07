package com.fixmystreet.fixmystreet.services;

import com.fixmystreet.fixmystreet.model.User;
import com.fixmystreet.fixmystreet.model.enums.EmailVerificationToken;
import org.springframework.scheduling.annotation.Async;

public interface EmailVerificationService {

    public EmailVerificationToken createToken(User user);

    @Async
    public void sendVerificationEmail(User user, String appUrl);
}
