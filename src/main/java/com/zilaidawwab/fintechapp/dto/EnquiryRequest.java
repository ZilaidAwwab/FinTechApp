package com.zilaidawwab.fintechapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryRequest {

    @Schema(name = "Account Number to Enquire Balance")
    private String accountNumber;
}
