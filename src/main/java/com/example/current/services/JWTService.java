package com.example.current.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.current.model.LocalUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String key;
    @Value("${jwt.token.lifetime}")
    private long lifetime;

    public String generateToken(LocalUser user) {
        return JWT.create()
                .withClaim("USERNAME",user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 1000 * lifetime)))
                .sign(Algorithm.HMAC256(key));


    }
    public String getUsernameFromJwt(String token) {
        return JWT.decode(token).getSubject();
    }
}
