package com.example.current.services;

import com.example.current.api.model.LoginBody;
import com.example.current.api.model.LoginResponse;
import com.example.current.api.model.RegistrationBody;
import com.example.current.exceptions.*;
import com.example.current.mappers.UserMapper;
import com.example.current.model.LocalUser;
import com.example.current.model.VerificationJWTEmailToken;
import com.example.current.repository.UserRepository;
import com.example.current.repository.VerificationJWTEmailTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;
    private final EmailService emailService;
    private final VerificationJWTEmailTokenRepository verificationJWTEmailTokenRepository;
    public Long register(RegistrationBody registrationBody)  {
        var email = registrationBody.getEmail();
        var username = registrationBody.getUsername();
        if (repository.existsByEmail(email)){
            throw new UserEmailAlreadyExist(String.format("User with email '%s' already exist",email),HttpStatus.CONFLICT);
        }
        if (repository.existsByUsername(registrationBody.getUsername())){
            throw new UsernameAlreadyExist(String.format("User with username '%s' already exist",username),HttpStatus.CONFLICT);
        }
        var user = mapper.fromRegistrationBodyToUser(registrationBody);
        var token = createVerificationToken(user);
        repository.save(user);
        try {
            emailService.sendVerificationMail(token);
            verificationJWTEmailTokenRepository.save(token);
        } catch (EmailFailureException ex) {
            ex.printStackTrace();
        }
        return user.getId();
    }

    public LoginResponse login(LoginBody loginBody)  {
        var user = repository.findByUsername(loginBody.getUsername());

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username '%s' not found", loginBody.getUsername()), HttpStatus.BAD_REQUEST);
        }

        LocalUser foundUser = user.get();
        if (!encryptionService.verifyPassword(foundUser.getPassword(), loginBody.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        if (foundUser.getEmailVerified()){
            return new LoginResponse(jwtService.generateToken(foundUser),true,"");
        } else {
            try {
                resendVerificationTokenIfNeeded(foundUser);
            } catch (UserNotVerifiedException ex) {
                    throw new EmailNotVerifiedException("Your account not verified email address ",HttpStatus.CONFLICT);
            }
        }
        return null;
    }


    private void resendVerificationTokenIfNeeded(LocalUser user) throws EmailFailureException, UserNotVerifiedException {
        List<VerificationJWTEmailToken> verificationTokens = user.getVerificationTokens();
        boolean resend = verificationTokens.isEmpty()
                || verificationTokens.get(0).getExpiresAt().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));

        if (resend) {
            VerificationJWTEmailToken verificationToken = createVerificationToken(user);
            verificationJWTEmailTokenRepository.save(verificationToken);
            emailService.sendVerificationMail(verificationToken);
        }
            throw new UserNotVerifiedException(resend);

    }

    public VerificationJWTEmailToken createVerificationToken(LocalUser user) {
        var token = VerificationJWTEmailToken.builder()
                .token(jwtService.generateVerificationJWTEmailToken(user)).build();
        token.setExpiresAt(new Timestamp(System.currentTimeMillis()));
        token.setUser(user);
        user.getVerificationTokens().add(token);

        return token;
    }
    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationJWTEmailToken> opToken = verificationJWTEmailTokenRepository.findByToken(token);
        if (opToken.isPresent()) {
            var verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if (!user.getEmailVerified()) {
                user.setEmailVerified(true);
                repository.save(user);
                verificationJWTEmailTokenRepository.deleteByUserId(user.getId());
                return true;
            }
        }
        return false;
    }

}
