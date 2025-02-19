package com.zilaidawwab.fintechapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequest {

    @Schema(name = "Sender Account Number")
    private String sourceAccountNumber;

    @Schema(name = "Recipient Account Number")
    private String destinationAccountNumber;

    @Schema(name = "Transfer Amount")
    private BigDecimal amount;
}
