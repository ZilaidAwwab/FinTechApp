package com.zilaidawwab.fintechapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    // these annotations are for swagger ui, not part of functionality in spring boot
    @Schema(name = "User Account Name") // this will be shown in the UI in place of these variable names
    private String accountName;

    @Schema(name = "User Account Balance")
    private BigDecimal accountBalance;

    @Schema(name = "User Account Number")
    private String accountNumber;
}
