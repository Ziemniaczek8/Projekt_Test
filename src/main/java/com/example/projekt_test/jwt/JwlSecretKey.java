package com.example.projekt_test.jwt;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwlSecretKey {

    public final JwtConfig jwtConfig;

    public JwlSecretKey(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }
}
