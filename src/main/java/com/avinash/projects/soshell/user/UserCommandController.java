package com.avinash.projects.soshell.user;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.avinash.projects.soshell.user.exeptions.InvalidCredException;
import com.avinash.projects.soshell.user.exeptions.UserAlreadyExistsException;
import com.avinash.projects.soshell.user.exeptions.UserNotFoundException;

import jakarta.validation.constraints.Size;
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
        return "User related commands. Usage only with sub-commands - signup,login";
    }

    @ShellMethod(key = "user signup", value = "signup flow for a new user")
    public String userSignUp(@ShellOption(value = { "--username", "-u" }) String username,
            @ShellOption(value = { "--password", "-p" }) String password) {
        log.info("new signup request for username {}", username);
        try {
            userService.signupUser(username, password);
        } catch (UserAlreadyExistsException e) {
            log.info(e.getMessage());
            return e.getMessage();
        }
        return "signup successful";
    }

    @ShellMethod(key = "user login", value = "login flow for an user")
    public String userLogin(@ShellOption(value = { "--username", "-u" }) String username,
            @ShellOption(value = { "--password", "-p" }) String password) {
        log.info("new login request for username {}", username);
        try {
            userService.loginUser(username, password);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return e.getMessage();
        } catch (InvalidCredException e) {
            log.info(e.getMessage());
            return e.getMessage();
        }
        return "login successful";
    }

    @ShellMethod(key = "user view", value = "view an user")
    public String userLogout(@ShellOption(value = { "--username", "-u" }, defaultValue = ShellOption.NULL) String username) {
        log.info("view request for user {}", username);
        User user = null;
        try {
            user = userService.getUserByUsername(username);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return e.getMessage();
        }
        return user.toString();
    }

    @ShellMethod(key = "user follow", value = "follow user with username")
    public String userFollow(@ShellOption(value = { "--username", "-u" }) String username) {
        return "user " + username + " followed";
    }

    @ShellMethod(key = "user unfollow", value = "unfollow user with username")
    public String userUnfollow(@ShellOption(value = { "--username", "-u" }) String username) {
        return "user " + username + " unfollowed";
    }

    @ShellMethod(key = "user following", value = "list of users followed by user")
    public String userFollowing(
            @ShellOption(value = { "--limit", "-l" }, defaultValue = "5") @Size(min = 1, max = 5) int limit,
            @ShellOption(value = { "--page", "-p" }, defaultValue = "1") @Size(min = 1) int page) {
        return "user's following list";
    }

    @ShellMethod(key = "user followers", value = "list of users following user")
    public String userFollowedBy(@ShellOption(value = { "--limit", "-l" }) @Size(min = 1, max = 5) int limit,
            @ShellOption(value = { "--page", "-p" }) @Size(min = 1) int page) {
        return "user's followers list";
    }

    @ShellMethod(key = "user delete", value = "delete user")
    public String userDelete(@ShellOption(value = { "--token", "-t" }, defaultValue = "-1") String token,
            @ShellOption(value = { "--hard", "-h" }, defaultValue = "false") boolean hard) {
        Boolean hardDelete = false;

        if (token.equals("-1")) {
            return "token";
        } else {
            // verify token
            // hard delete
            hardDelete = true;
        }
        if (hard && hardDelete) {
            // perform hard delete
        }
        return "user deleted";
    }

    @ShellMethod(key = "user update", value = "fields that can be updated for user")
    public String userUpdate(@ShellOption(value = { "--setting-name", "-n" }) String settingName,
            @ShellOption(value = { "--setting-val", "-v" }) String settingValue) {

        if (!settingName.isBlank() && !settingValue.isBlank()) {
            // update setting
            return "user updated";
        }
        return "Fields that can be updated with val";
    }

}
