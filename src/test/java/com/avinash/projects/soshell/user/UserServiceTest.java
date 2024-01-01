package com.avinash.projects.soshell.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.avinash.projects.soshell.user.exeptions.InvalidCredException;
import com.avinash.projects.soshell.user.exeptions.UserAlreadyExistsException;
import com.avinash.projects.soshell.user.exeptions.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void testDeleteUser() {
        String username = "username";
        String password = "password";
        String passwordHash = Utils.getPasswordHash(password);
        Mockito.when(userRepository.userPresentWithUsername(username)).thenReturn(true);
        Mockito.when(userRepository.getPasswordForUsername(username)).thenReturn(passwordHash);
        userService.deleteUser(username, password);
    }

    @Test
    void testDeleteUser_userNotFoundException() {
        String username = "username";
        String password = "password";
        UserNotFoundException thrown = assertThrows(
                UserNotFoundException.class,
                () -> userService.deleteUser(username, password));
        assertTrue(thrown.getMessage().contains(String.format("user with username %s doesn't exists", username)));

    }

    @Test
    void testGetUserByUsername() {
        String username = "username";
        Mockito.when(userRepository.userPresentWithUsername(username)).thenReturn(true);
        userService.getUserByUsername(username);
    }

    @Test
    void testGetUserByUsername_userNotFoundException() {
        String username = "username";
        UserNotFoundException thrown = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserByUsername(username));
        assertTrue(thrown.getMessage().contains(String.format("user with username %s doesn't exists", username)));
    }

    @Test
    void testLoginUser() {
        String username = "username";
        String password = "password";
        String passwordHash = Utils.getPasswordHash(password);
        Mockito.when(userRepository.userPresentWithUsername(username)).thenReturn(true);
        Mockito.when(userRepository.getPasswordForUsername(username)).thenReturn(passwordHash);
        userService.loginUser(username, password);
    }

    @Test
    void testLoginUser_invalidCredException() {
        String username = "username";
        String password = "password";
        String savedPasswordHash = Utils.getPasswordHash("password1");
        Mockito.when(userRepository.userPresentWithUsername(username)).thenReturn(true);
        Mockito.when(userRepository.getPasswordForUsername(username)).thenReturn(savedPasswordHash);
        InvalidCredException thrown = assertThrows(
                InvalidCredException.class,
                () -> userService.loginUser(username, password));
        assertTrue(thrown.getMessage().contains(String.format("Invalid credentials for username %s", username)));
    }

    @Test
    void testSignupUser() {
        String username = "username";
        String password = "password";
        Mockito.when(userRepository.userPresentWithUsername(username)).thenReturn(false);
        userService.signupUser(username, password);

    }

    @Test
    void testSignupUser_userAlreadyExistsException() {
        String username = "username";
        String password = "password";
        Mockito.when(userRepository.userPresentWithUsername(username)).thenReturn(true);
        UserAlreadyExistsException thrown = assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.signupUser(username, password));
        assertTrue(thrown.getMessage().contains(String.format("user with username %s already exists", username)));

    }
}
