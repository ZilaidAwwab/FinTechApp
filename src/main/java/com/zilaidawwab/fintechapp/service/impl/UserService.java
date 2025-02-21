package com.zilaidawwab.fintechapp.service.impl;

import com.zilaidawwab.fintechapp.dto.*;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
    BankResponse login(LoginDto loginDto);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);
    BankResponse creditAccount(CreditDebitRequest creditDebitRequest);
    BankResponse debitAccount(CreditDebitRequest creditDebitRequest);
    BankResponse transfer(TransferRequest transferRequest);
}
