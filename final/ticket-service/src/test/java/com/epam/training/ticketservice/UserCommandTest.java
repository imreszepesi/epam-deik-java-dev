package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.ui.command.UserCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class UserCommandTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserCommand userCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void adminLogin_ShouldReturnFailureMessage_WhenAdminLoginFails() {
        // Arrange
        when(userService.adminLogin(any(), any())).thenReturn(Optional.empty());

        // Act
        String result = userCommand.adminLogin("admin", "incorrectPassword");

        // Assert
        assertEquals("Login failed due to incorrect credentials", result);
    }

    @Test
    void login_ShouldReturnFailureMessage_WhenUserLoginFails() {
        // Arrange
        when(userService.login(any(), any())).thenReturn(Optional.empty());

        // Act
        String result = userCommand.login("user", "incorrectPassword");

        // Assert
        assertEquals("No such username or password!", result);
    }

    @Test
    void description_ShouldReturnNotLoggedInMessage_WhenUserIsNotLoggedIn() {
        // Arrange
        when(userService.describe()).thenReturn(Optional.empty());

        // Act
        String result = userCommand.description();

        // Assert
        assertEquals("You are not signed in", result);
    }


    @Test
    void registerUser_ShouldReturnFailureMessage_WhenUserRegistrationFails() {
        // Arrange
        // Simulating a failure by throwing an exception
        doThrow(new RuntimeException()).when(userService).registerUser(any(), any());

        // Act
        String result = userCommand.registerUser("newUser", "newPassword");

        // Assert
        assertEquals("Registration failed!", result);
    }
}


