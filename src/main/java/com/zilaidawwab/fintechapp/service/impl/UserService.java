package com.zilaidawwab.fintechapp.service.impl;

import com.zilaidawwab.fintechapp.dto.BankResponse;
import com.zilaidawwab.fintechapp.dto.CreditDebitRequest;
import com.zilaidawwab.fintechapp.dto.EnquiryRequest;
import com.zilaidawwab.fintechapp.dto.TransferRequest;
import com.zilaidawwab.fintechapp.dto.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);
    BankResponse creditAccount(CreditDebitRequest creditDebitRequest);
    BankResponse debitAccount(CreditDebitRequest creditDebitRequest);
    BankResponse transfer(TransferRequest transferRequest);
}
