package com.epam.training.ticketservice;


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
    void testLogin() {
        // Arrange
        String username = "user";
        String password = "password";
        User user = new User(username, password, User.Role.USER);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));

        // Act
        Optional<UserDto> loggedInUser = userService.login(username, password);

        // Assert
        assertTrue(loggedInUser.isPresent());
        assertEquals(username, loggedInUser.get().getUsername());
        assertEquals(password, loggedInUser.get().getPassword());
        assertEquals(User.Role.USER, loggedInUser.get().getRole());
    }

    @Test
    void testLoginInvalidCredentials() {
        // Arrange
        String username = "nonexistent";
        String password = "invalid";

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        // Act
        Optional<UserDto> loggedInUser = userService.login(username, password);

        // Assert
        assertTrue(loggedInUser.isEmpty());
    }

    @Test
    void testAdminLogin() {
        // Arrange
        String username = "admin";
        String password = "admin";
        User adminUser = new User(username, password, User.Role.ADMIN);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(adminUser));

        // Act
        Optional<UserDto> loggedInAdmin = userService.adminLogin(username, password);

        // Assert
        assertTrue(loggedInAdmin.isPresent());
        assertEquals(username, loggedInAdmin.get().getUsername());
        assertEquals(password, loggedInAdmin.get().getPassword());
        assertEquals(User.Role.ADMIN, loggedInAdmin.get().getRole());
    }

    @Test
    void testAdminLoginInvalidCredentials() {
        // Arrange
        String username = "nonexistent";
        String password = "invalid";

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        // Act
        Optional<UserDto> loggedInAdmin = userService.adminLogin(username, password);

        // Assert
        assertTrue(loggedInAdmin.isEmpty());
    }

    @Test
    void testLogout() {
        // Arrange
        String username = "user";
        String password = "password";
        User user = new User(username, password, User.Role.USER);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));

        // Login first
        userService.login(username, password);

        // Act
        Optional<UserDto> loggedOutUser = userService.logout();

        // Assert
        assertTrue(loggedOutUser.isPresent());
        assertEquals(username, loggedOutUser.get().getUsername());
        assertEquals(password, loggedOutUser.get().getPassword());
        assertEquals(User.Role.USER, loggedOutUser.get().getRole());

        // Check that the user is actually logged out
        Optional<UserDto> loggedInUser = userService.describe();
        assertTrue(loggedInUser.isEmpty());
    }

    @Test
    void testDescribe() {
        // Arrange
        String username = "user";
        String password = "password";
        User user = new User(username, password, User.Role.USER);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));

        // Login first
        userService.login(username, password);

        // Act
        Optional<UserDto> loggedInUser = userService.describe();

        // Assert
        assertTrue(loggedInUser.isPresent());
        assertEquals(username, loggedInUser.get().getUsername());
        assertEquals(password, loggedInUser.get().getPassword());
        assertEquals(User.Role.USER, loggedInUser.get().getRole());
    }

    @Test
    void testRegisterUser() {
        // Arrange
        String username = "newUser";
        String password = "newPassword";

        // Act
        userService.registerUser(username, password);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }
}
