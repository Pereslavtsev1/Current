package com.example.current.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.current.model.LocalUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String key;
    @Value("${jwt.token.token-lifetime}")
    private long tokenLifetime;
    @Value("${jwt.token.email-token-lifetime}")
    private long emailLifetime;

    public String generateToken(LocalUser user) {
        return JWT.create()
                .withClaim("USERNAME",user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 1000 * tokenLifetime)))
                .sign(Algorithm.HMAC256(key));


    }
    public String generateVerificationJWTEmailToken(LocalUser user) {
        return JWT.create()
                .withClaim("EMAIL_KEY",user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 1000 * emailLifetime)))
                .sign(Algorithm.HMAC256(key));


    }
    public String generatePasswordResetJWT(LocalUser user) {
        return JWT.create()
                .withClaim("PASSWORD_RESET_EMAIL",user.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 1000 * 30)))
                .sign(Algorithm.HMAC256(key));

    }
    public String getResetPasswordEmailFromJWT(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(key)).build().verify(token);
        return jwt.getClaim("PASSWORD_RESET_EMAIL").asString();
    }
    public String getUsernameFromJwt(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(key)).build().verify(token)     ;
        return JWT.decode(token).getClaim("USERNAME").asString();
    }
}
