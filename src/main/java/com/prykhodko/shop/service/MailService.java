package com.prykhodko.shop.service;

import com.prykhodko.shop.model.ConfirmationCode;

public interface MailService {
    void sendConfirmCode(ConfirmationCode code);
}
