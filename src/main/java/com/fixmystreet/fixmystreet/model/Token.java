package com.fixmystreet.fixmystreet.model;

import jakarta.persistence.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @Column(name = "token_type")
    private String tokenType = OAuth2AccessToken.TokenType.BEARER.getValue();

    private boolean revoked;
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
