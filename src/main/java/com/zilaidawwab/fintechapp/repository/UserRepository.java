package com.zilaidawwab.fintechapp.repository;

import com.zilaidawwab.fintechapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Database "User" whose primary key is "Long" are passed in the args
public interface UserRepository extends JpaRepository<User, Long> {

    // Here we are checking whether the user exists by checking if the email is already used
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    User findByAccountNumber(String accountNumber);
}
