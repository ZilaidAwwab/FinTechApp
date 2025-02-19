package com.zilaidawwab.fintechapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditDebitRequest {

    @Schema(name = "User Account Number")
    private String accountNumber;

    @Schema(name = "Amount that user wants to credit or debit")
    private BigDecimal amount;
}
