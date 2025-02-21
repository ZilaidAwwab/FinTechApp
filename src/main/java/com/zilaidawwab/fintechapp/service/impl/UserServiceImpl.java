package com.zilaidawwab.fintechapp.service.impl;

import com.zilaidawwab.fintechapp.config.JwtTokenProvider;
import com.zilaidawwab.fintechapp.dto.*;
import com.zilaidawwab.fintechapp.entity.Role;
import com.zilaidawwab.fintechapp.entity.User;
import com.zilaidawwab.fintechapp.repository.UserRepository;
import com.zilaidawwab.fintechapp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {

    // we are wiring this class to the data layer so to check if the user already exists
    // we have a method initialized in the user repo interface that we use to check the
    // existence of the user in the DB
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        // check if the user already has an account
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // creating an account: saving a new user into the DB
        User newUser = User.builder() // this is because we used the Builder annotation with the User entity class
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .role(Role.valueOf("ROLE_USER"))
                .build();

        User savedUser = userRepository.save(newUser);

        // send email alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("Account Creation")
                .messageBody("Congratulations! Your Account has been successfully created.\nYour Account Details: \n" +
                        "Account Name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName() + "\n" +
                        "Account Number: " + savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .build())
                .build();
    }

    public BankResponse login(LoginDto loginDto) {
        Authentication authentication;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        EmailDetails loginAlert = EmailDetails.builder()
                .subject("You're logged in")
                .recipient(loginDto.getEmail())
                .messageBody("You logged in to your account. If you didn't initiated the request, please contact the bank support.")
                .build();
        emailService.sendEmailAlert(loginAlert);

        return BankResponse.builder()
                .responseCode("Login Success")
                .responseMessage(jwtTokenProvider.generateToken(authentication))
                .build();
    }

    // balance enquiry, name enquiry, credit, debit, transfer
    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        // check if provided account number exists in DB
        boolean ifAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!ifAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(enquiryRequest.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        // check if an account with the provided name exists in DB
        boolean ifAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!ifAccountExists) {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        // checking if the account exists
        boolean ifAccountExists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if (!ifAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userToCredit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitRequest.getAmount()));
        userRepository.save(userToCredit);

        // save transaction
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionType("CREDIT")
                .amount(creditDebitRequest.getAmount())
                .accountNumber(userToCredit.getAccountNumber())
                .status("SUCCESS")
                .build();
        transactionService.saveTransaction(transactionDto);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(userToCredit.getAccountNumber())
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        // check if the account exists
        boolean ifAccountExists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if (!ifAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        // check if the amount we intend to withdraw is more than balance
        User userToDebit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = creditDebitRequest.getAmount().toBigInteger();
        if (availableBalance.intValue() < debitAmount.intValue()) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_ACCOUNT_BALANCE)
                    .responseMessage(AccountUtils.INSUFFICIENT_ACCOUNT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        } else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditDebitRequest.getAmount()));
            // after making any alteration to the DB value must call the save method
            userRepository.save(userToDebit);

            // save transaction
            TransactionDto transactionDto = TransactionDto.builder()
                    .transactionType("CREDIT")
                    .amount(creditDebitRequest.getAmount())
                    .accountNumber(userToDebit.getAccountNumber())
                    .status("SUCCESS")
                    .build();
            transactionService.saveTransaction(transactionDto);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(userToDebit.getAccountBalance())
                            .accountNumber(userToDebit.getAccountNumber())
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                            .build())
                    .build();
        }
    }

    @Override
    public BankResponse transfer(TransferRequest transferRequest) {
        // get the account debit (check if it exists)
        boolean isDestinationAccountExists = userRepository.existsByAccountNumber(transferRequest.getDestinationAccountNumber());
        if (!isDestinationAccountExists) {
                return BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                        .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                        .accountInfo(null)
                        .build();
            }
        
        // check the amount debited is not more than the current balance
        User sourceAccountUser = userRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());
        if (transferRequest.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_ACCOUNT_BALANCE)
                    .responseMessage(AccountUtils.INSUFFICIENT_ACCOUNT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // debit the account
        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(transferRequest.getAmount()));
        String sourceUsername = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName() + " " + sourceAccountUser.getOtherName();
        userRepository.save(sourceAccountUser);

        // sending email to the user whose account has been debited
        EmailDetails debitAlert = EmailDetails.builder()
                .subject("Account Debit Alert")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("The sum of " + transferRequest.getAmount() + " has been deducted from your account. Your current account balance is " + sourceAccountUser.getAccountBalance())
                .attachment(null)
                .build();
        emailService.sendEmailAlert(debitAlert);

        // save transaction
        TransactionDto debitTransactionDto = TransactionDto.builder()
                .transactionType("DEBIT")
                .amount(transferRequest.getAmount())
                .accountNumber(transferRequest.getSourceAccountNumber())
                .status("SUCCESS")
                .build();
        transactionService.saveTransaction(debitTransactionDto);

        // get the account to credit
        User destinationAccountUser = userRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());

        // credit the account
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transferRequest.getAmount()));
        userRepository.save(destinationAccountUser);

        // sending email to the user whose account has been credited
        EmailDetails creditAlert = EmailDetails.builder()
                .subject("Account Credit Alert")
                .recipient(destinationAccountUser.getEmail())
                .messageBody("The sum of " + transferRequest.getAmount() + " has been sent to your account from " + sourceUsername + ". Your current account balance is " + destinationAccountUser.getAccountBalance())
                .attachment(null)
                .build();
        emailService.sendEmailAlert(creditAlert);

        // save transaction
        TransactionDto creditTransactionDto = TransactionDto.builder()
                .transactionType("CREDIT")
                .amount(transferRequest.getAmount())
                .accountNumber(transferRequest.getDestinationAccountNumber())
                .status("SUCCESS")
                .build();
        transactionService.saveTransaction(creditTransactionDto);

        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_CODE_MESSAGE)
                .accountInfo(null)
                .build();
    }
}
