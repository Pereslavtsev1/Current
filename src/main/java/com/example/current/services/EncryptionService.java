package com.example.current.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptionService {
    private final PasswordEncoder passwordEncoder;
    public boolean verifyPassword(String password,String inputPassword) {
        return passwordEncoder.matches(inputPassword,password);
    }
}
