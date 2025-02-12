package com.zilaidawwab.fintechapp.service.impl;

import com.zilaidawwab.fintechapp.dto.BankResponse;
import com.zilaidawwab.fintechapp.dto.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
}
