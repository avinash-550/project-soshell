package com.avinash.projects.soshell.user;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.avinash.projects.soshell.user.exeptions.InvalidCredException;
import com.avinash.projects.soshell.user.exeptions.UserAlreadyExistsException;
import com.avinash.projects.soshell.user.exeptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ShellComponent
public class UserCommandController {
    private UserService userService;

    public UserCommandController(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod(key = "user", value = "user related commands")
    public String user() {
        return "User related commands. Usage only with sub-commands - signup,login,view,delete";
    }

    @ShellMethod(key = "user signup", value = "signup flow for a new user")
    public String userSignUp(
            @ShellOption(value = { "--username", "-u" }, defaultValue = ShellOption.NULL) String username,
            @ShellOption(value = { "--password", "-p" }, defaultValue = ShellOption.NULL) String password) {
        log.info("new signup request for username {}", username);
        try {
            userService.signupUser(username, password);
        } catch (NullPointerException e) {
            log.info(e.getMessage());
            return "bad request - username or password should not be null";
        } catch (UserAlreadyExistsException e) {
            log.info(e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            log.info(e.toString());
            return "internal server error";
        }
        return "signup successful";
    }

    @ShellMethod(key = "user login", value = "login flow for an user")
    public String userLogin(
            @ShellOption(value = { "--username", "-u" }, defaultValue = ShellOption.NULL) String username,
            @ShellOption(value = { "--password", "-p" }, defaultValue = ShellOption.NULL) String password) {
        log.info("new login request for username {}", username);
        try {
            userService.loginUser(username, password);
        } catch (NullPointerException e) {
            log.info(e.getMessage());
            return "bad request - username or password should not be null";
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return e.getMessage();
        } catch (InvalidCredException e) {
            log.info(e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            log.info(e.getMessage());
            return "internal server error";
        }
        return "login successful";
    }

    @ShellMethod(key = "user view", value = "view an user")
    public String userView(
            @ShellOption(value = { "--username", "-u" }, defaultValue = ShellOption.NULL) String username) {
        log.info("view request for user {}", username);
        User user = null;
        try {
            user = userService.getUserByUsername(username);
        } catch (NullPointerException e) {
            log.info(e.getMessage());
            return "bad request - username should not be null";
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            log.info(e.getMessage());
            return "internal server error";
        }
        return user.toString();
    }

    @ShellMethod(key = "user delete", value = "delete user with username")
    public String userDelete(
            @ShellOption(value = { "--username", "-u" }, defaultValue = ShellOption.NULL) String username,
            @ShellOption(value = { "--password", "-p" }, defaultValue = ShellOption.NULL) String password) {
        log.info("delete request for username {}", username);
        try {
            userService.deleteUser(username, password);
        } catch (NullPointerException e) {
            log.info(e.getMessage());
            return "bad request - username or password should not be null";
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return e.getMessage();
        } catch (InvalidCredException e) {
            log.info(e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            log.info(e.getMessage());
            return "internal server error";
        }
        return "user " + username + " deleted";
    }
}
