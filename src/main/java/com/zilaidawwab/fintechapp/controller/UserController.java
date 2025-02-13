package com.zilaidawwab.fintechapp.controller;

import com.zilaidawwab.fintechapp.dto.BankResponse;
import com.zilaidawwab.fintechapp.dto.EnquiryRequest;
import com.zilaidawwab.fintechapp.dto.UserRequest;
import com.zilaidawwab.fintechapp.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return userService.nameEnquiry(enquiryRequest);
    }
}
