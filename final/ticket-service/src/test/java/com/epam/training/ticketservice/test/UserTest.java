package com.epam.training.ticketservice.test;


import com.epam.training.ticketservice.core.user.UserServiceImpl;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginWithValidCredentials() {
        // Arrange
        User user = new User("testUser", "password", User.Role.USER);
        when(userRepository.findByUsernameAndPassword("testUser", "password")).thenReturn(Optional.of(user));

        // Act
        Optional<UserDto> result = userService.login("testUser", "password");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
        assertEquals("password", result.get().getPassword());
        assertEquals(User.Role.USER, result.get().getRole());
    }

    @Test
    void testLoginWithInvalidCredentials() {
        // Arrange
        when(userRepository.findByUsernameAndPassword("invalidUser", "invalidPassword")).thenReturn(Optional.empty());

        // Act
        Optional<UserDto> result = userService.login("invalidUser", "invalidPassword");

        // Assert
        assertTrue(result.isEmpty());
    }

    // Similar tests for other methods (adminLogin, logout, describe, registerUser) can be written

    @Test
    void testLogout() {
        // Arrange
        userService.login("testUser", "password");

        // Act
        Optional<UserDto> result = userService.logout();

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
    }

    @Test
    void testDescribeWhenLoggedIn() {
        // Arrange
        userService.login("testUser", "password");

        // Act
        Optional<UserDto> result = userService.describe();

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
    }

    @Test
    void testDescribeWhenNotLoggedIn() {
        // Act
        Optional<UserDto> result = userService.describe();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testRegisterUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(new User("newUser", "newPassword", User.Role.USER));

        // Act
        userService.registerUser("newUser", "newPassword");

        // Assert - You can add additional assertions based on your requirements
        verify(userRepository, times(1)).save(any(User.class));
    }
}
