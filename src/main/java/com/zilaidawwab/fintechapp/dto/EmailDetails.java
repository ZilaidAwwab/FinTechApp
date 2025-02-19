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
public class EmailDetails {

    @Schema(name = "Recipient User Email Id")
    private String recipient;

    @Schema(name = "Email Body")
    private String messageBody;

    @Schema(name = "Email Subject Line")
    private String subject;

    @Schema(name = "Attachment file to sent with Email")
    private String attachment;
}
