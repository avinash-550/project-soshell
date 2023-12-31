package com.avinash.projects.soshell.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT count(u.id) > 0 FROM users u WHERE u.username = :username")
    boolean userPresentWithUsername(@Param("username") String username);

    @Query("SELECT u.password FROM users u WHERE u.username = :username")
    String getPasswordForUsername(String username);

    @Query("SELECT u FROM users u WHERE u.username = :username")
    User findByUsername(String username);
}
