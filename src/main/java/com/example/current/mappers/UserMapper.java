package com.example.current.mappers;

import com.example.current.api.model.RegistrationBody;
import com.example.current.model.LocalUser;
import com.example.current.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    public LocalUser fromRegistrationBodyToUser(RegistrationBody registrationBody) {
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(passwordEncoder.encode(registrationBody.getPassword()));
        return user;
    }
}
