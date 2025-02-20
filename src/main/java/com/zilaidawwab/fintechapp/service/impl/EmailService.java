package com.zilaidawwab.fintechapp.service.impl;

import com.zilaidawwab.fintechapp.dto.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailWithAttachment(EmailDetails emailDetails);
}
