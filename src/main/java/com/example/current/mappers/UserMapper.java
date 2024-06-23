package com.example.current.mappers;

import com.example.current.api.model.RegistrationBody;
import com.example.current.model.LocalUser;
import com.example.current.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public LocalUser fromRegistrationBodyToUser(RegistrationBody registrationBody) {
        return LocalUser.builder()
                .email(registrationBody.email())
                .password(passwordEncoder.encode(registrationBody.password()))
                .username(registrationBody.username())
                .firstName(registrationBody.firstName())
                .lastName(registrationBody.lastName())
                .build();
    }
}
