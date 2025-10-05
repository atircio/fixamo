package com.fixmystreet.fixmystreet.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private  String secretKey;
    private final long expirationTime = 3600000L;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String username){
        return Jwts.builder()
                .issuedAt(Date.from(Instant.now()))
                .subject(username)
                .expiration(new Date(expirationTime))
                .signWith(getSigningKey()).compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
