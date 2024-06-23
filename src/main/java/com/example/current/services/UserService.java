package com.example.current.services;

import com.example.current.api.model.LoginBody;
import com.example.current.api.model.LoginResponse;
import com.example.current.api.model.RegistrationBody;
import com.example.current.exceptions.UserEmailAlreadyExist;
import com.example.current.exceptions.UsernameAlreadyExist;
import com.example.current.mappers.UserMapper;
import com.example.current.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;
    public Long register(RegistrationBody registrationBody) {
        var email = registrationBody.email();
        var username = registrationBody.username();
        if (repository.existsByEmail(email)){
            throw new UserEmailAlreadyExist(String.format("User with email %s already exist",email),HttpStatus.CONFLICT);
        }
        if (repository.existsByUsername(registrationBody.username())){
            throw new UsernameAlreadyExist(String.format("User with username %s already exist",username),HttpStatus.CONFLICT);
        }
        return repository.save(mapper.fromRegistrationBodyToUser(registrationBody)).getId();
    }

    public LoginResponse login(LoginBody loginBody) {
        var user = repository.findByUsername(loginBody.username());
        if (user.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s not found",loginBody.username()));
        }
        if (!encryptionService.verifyPassword(user.get().getPassword(), loginBody.password())){
            throw new BadCredentialsException("Incorrect password");
        }
        return new LoginResponse(jwtService.generateToken(user.get()));
    }
}
