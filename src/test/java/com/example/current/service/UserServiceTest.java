package com.example.current.service;

import com.example.current.api.model.LoginBody;
import com.example.current.api.model.RegistrationBody;
import com.example.current.exceptions.UserEmailAlreadyExist;
import com.example.current.exceptions.UsernameAlreadyExist;
import com.example.current.services.UserService;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor

public class UserServiceTest {

    private final UserService userService;
    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "secret"))
            .withPerMethodLifecycle(true);

    @Test
    @Transactional
    public void testRegisterUser() throws MessagingException {
        RegistrationBody body = RegistrationBody.builder()
                .username("UserA")
                .email("UserServiceTest$testRegisterUser@junit.com")
                .firstName("FirstName")
                .lastName("LastName")
                .password("MySecretPassword123")
                .build();

        Assertions.assertThrows(UsernameAlreadyExist.class,
                () -> userService.register(body), String.format("User with username '%s' already exists", body.getUsername()));

        body.setUsername("UserServiceTest$testRegisterUser");
        body.setEmail("UserA@junit.com");

        Assertions.assertThrows(UserEmailAlreadyExist.class,
                () -> userService.register(body), String.format("User with email '%s' already exists", body.getEmail()));

        Assertions.assertDoesNotThrow(
                () -> userService.register(body), "User was registered successfully");
        Assertions.assertDoesNotThrow(() -> userService.register(body),
                "User should register successfully.");
        Assertions.assertEquals(body.getEmail(), greenMailExtension.getReceivedMessages()[0]
                .getRecipients(Message.RecipientType.TO)[0].toString());
    }
    @Test
    @Transactional
    public void testLoginUser() throws MessagingException {
        LoginBody loginBody = new LoginBody();

    }
}
