package com.fixmystreet.fixmystreet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    private Token(Builder builder) {
        this.token = builder.token;
        this.tokenType = builder.tokenType;
        this.expired = builder.expired;
        this.revoked = builder.revoked;
    }


    public static class Builder {
        private String token;
        private String tokenType = OAuth2AccessToken.TokenType.BEARER.getValue();
        private boolean revoked;
        private boolean expired;
        private User user;

        public Builder(String token, User user){
            this.token = token;
            this.user = user;
        }

        public Builder revoked(boolean revoked){
            this.revoked = revoked;
            return this;
        }

        public Builder expired(boolean expired){
            this.expired = expired;
            return this;
        }

        public Builder user(User user){
            this.user = user;
            return this;
        }

        public Builder tokenType (String tokenType){
            this.tokenType = tokenType;
            return this;
        }

        public Token build(){
            return new Token(this);
        }
    }
}
