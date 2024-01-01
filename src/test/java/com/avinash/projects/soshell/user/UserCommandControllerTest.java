package com.avinash.projects.soshell.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.avinash.projects.soshell.user.exeptions.InvalidCredException;
import com.avinash.projects.soshell.user.exeptions.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserCommandControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserCommandController controller;

    @Test
    void testUserDelete() {
        String username = "username";
        String password = "password";
        Assertions.assertEquals(controller.userDelete(username, password), "user " + username + " deleted");
    }

    @Test
    void testUserDelete_exception() {
        String username = "username";
        String password = "password";
        Mockito.doThrow(new UserNotFoundException(String.format("user with username %s doesn't exists", username)))
                .when(userService).deleteUser(username, password);
        Assertions.assertEquals(controller.userDelete(username, password),
                String.format("user with username %s doesn't exists", username));
    }

    @Test
    void testUserLogin() {
        String username = "username";
        String password = "password";
        Assertions.assertEquals(controller.userLogin(username, password), "login successful");
    }

    @Test
    void testUserLogin_exception() {
        String username = "username";
        String password = "password";
        Mockito.doThrow(new InvalidCredException(String.format("Invalid credentials for username %s", username)))
                .when(userService).loginUser(username, password);
        Assertions.assertEquals(String.format("Invalid credentials for username %s", username),
                controller.userLogin(username, password));
    }

    @Test
    void testUserView() {
        String username = "username";
        String password = "password";
        User user = User.builder().username(username).password(password).build();
        Mockito.when(userService.getUserByUsername(username)).thenReturn(user);
        Assertions.assertEquals(user.toString(), controller.userView(username));

    }

    @Test
    void testUserView_exception() {
        String username = "username";
        Mockito.when(userService.getUserByUsername(username))
                .thenThrow(new UserNotFoundException(String.format("user with username %s doesn't exists", username)));
        Assertions.assertEquals(String.format("user with username %s doesn't exists", username),
                controller.userView(username));

    }

    @Test
    void testUserSignUp() {
        String username = "username";
        String password = "password";
        Assertions.assertEquals(controller.userSignUp(username, password), "signup successful");
    }

    @Test
    void testUserSignUp_exception() {
        String password = "password";
        Mockito.doThrow(new NullPointerException())
                .when(userService).signupUser(null, password);
        Assertions.assertEquals("bad request - username or password should not be null",
                controller.userSignUp(null, password));
    }

}
