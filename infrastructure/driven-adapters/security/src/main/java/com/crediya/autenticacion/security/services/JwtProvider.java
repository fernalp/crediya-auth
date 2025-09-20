package com.crediya.autenticacion.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final String issuer;
    private final Long expirationTime;
    private final SecretKey secretKey;

    public JwtProvider(
            @Value("${security.jwt.secretkey}") String secret,
            @Value("${security.jwt.issuer}") String issuer,
            @Value("${security.jwt.expirationTime}") Long expirationTime) {
        this.issuer = issuer;
        this.expirationTime = expirationTime;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Mono<String> generateToken(String email, String role) {
        return Mono.fromCallable(() -> Jwts
                .builder()
                .subject(email)
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .issuer(issuer)
                .signWith(secretKey)
                .compact());

    }

    public Mono<Claims> extractClaims(String token) {
        return Mono.fromCallable(() -> Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload());
    }

    public Mono<Boolean> isValidToken(String token, UserDetails userDetails) {
        return Mono.fromCallable(() -> Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject().equals(userDetails.getUsername())).flatMap(isValid -> {
                    if (isValid) {
                        return isTokenExpired(token).map(expired -> !expired);
                    } else {
                        return Mono.just(false);
                    }
                });
    }

    private Mono<Boolean> isTokenExpired(String token) {
        return extractExpiration(token).map(expiration -> expiration.before(new Date()));
    }

    public Mono<Date> extractExpiration(String token) {
        return Mono.fromCallable(() -> Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration());
    }



}
