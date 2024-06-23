package com.example.current.api.contollers.auth;

import com.example.current.api.model.LoginBody;
import com.example.current.api.model.LoginResponse;
import com.example.current.api.model.RegistrationBody;
import com.example.current.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Long> register(@Valid @RequestBody RegistrationBody registrationBody) {
        return ResponseEntity.ok(userService.register(registrationBody));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginBody loginBody) {
        return ResponseEntity.ok(userService.login(loginBody));
    }
}
