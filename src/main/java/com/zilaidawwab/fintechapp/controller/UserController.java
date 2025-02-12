package com.zilaidawwab.fintechapp.controller;

import com.zilaidawwab.fintechapp.dto.BankResponse;
import com.zilaidawwab.fintechapp.dto.UserRequest;
import com.zilaidawwab.fintechapp.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // We autowire the class or interface that we need to use in this class
    @Autowired
    UserService userService;

    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }
}
