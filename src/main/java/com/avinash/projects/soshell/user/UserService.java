package com.avinash.projects.soshell.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

import com.avinash.projects.soshell.user.exeptions.InvalidCredException;
import com.avinash.projects.soshell.user.exeptions.UserAlreadyExistsException;
import com.avinash.projects.soshell.user.exeptions.UserNotFoundException;

import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void signupUser(@NonNull String username,@NonNull String password) {
        try {
            if (userRepository.userPresentWithUsername(username)) {
                throw new UserAlreadyExistsException(String.format("user with username %s already exists", username));
            }
            userRepository.save(User.builder().username(username).password(Utils.getPasswordHash(password)).build());
            log.info("new user {} created", username);
        } catch (CannotCreateTransactionException e) {
            log.error("error while creating new user", e.getMessage());
        }
    }

    @Transactional
    public void loginUser(String username, String password) {
        try {
            if (!userRepository.userPresentWithUsername(username)) {
                throw new UserNotFoundException(String.format("user with username %s doesn't exists", username));
            }

            if (!StringUtils.equals(Utils.getPasswordHash(password), userRepository.getPasswordForUsername(username))) {
                throw new InvalidCredException(String.format("Invalid credentials for username %s", username));
            }
            log.info("login sucessful for username {}", username);
        } catch (CannotCreateTransactionException e) {
            log.error("error while user login", e.getMessage());
        }
    }

    @Transactional
    public User getUserByUsername(String username) {
        User user = null;
        try {
            if (!userRepository.userPresentWithUsername(username)) {
                throw new UserNotFoundException(String.format("user with username %s doesn't exists", username));
            }
            user = userRepository.findByUsername(username);
        } catch (CannotCreateTransactionException e) {
            log.error("error while fetching user by username", e.getMessage());
        }
        return user;
    }

    @Transactional
    public void deleteUser(String username, String password) {
        try {
            if (!userRepository.userPresentWithUsername(username)) {
                throw new UserNotFoundException(String.format("user with username %s doesn't exists", username));
            }
            if (!StringUtils.equals(Utils.getPasswordHash(password), userRepository.getPasswordForUsername(username))) {
                throw new InvalidCredException(String.format("Invalid credentials for username %s", username));
            }
            userRepository.deleteByUsername(username);
            log.info("user {} deleted", username);
        } catch (CannotCreateTransactionException e) {
            log.error("error while deleting user", e.getMessage());
        }
    }
}
