package com.prykhodko.shop.dao;

import com.prykhodko.shop.model.ConfirmationCode;
import com.prykhodko.shop.model.User;

import java.util.List;
import java.util.Optional;

public interface ConfirmationCodeDao {

    void addConfirmationCode(ConfirmationCode confirmationCode);

    void removeConfirmationCode(Long id);

    List<ConfirmationCode> getByUserEmail(String userEmail);

    Optional<ConfirmationCode> getLastConfirmationCodeForUser(User user);
}
