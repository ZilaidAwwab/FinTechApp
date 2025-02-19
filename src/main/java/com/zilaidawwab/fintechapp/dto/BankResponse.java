package com.zilaidawwab.fintechapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {

    @Schema(name = "Response Code on User Action Request")
    private String responseCode;

    @Schema(name = "Response Message on User Action Request")
    private String responseMessage;

    @Schema(name = "User Account Info returned after Action is Performed")
    private AccountInfo accountInfo;
}
