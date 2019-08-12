package com.prykhodko.shop.service.impl;


import com.prykhodko.shop.dao.ConfirmationCodeDao;
import com.prykhodko.shop.model.ConfirmationCode;
import com.prykhodko.shop.model.User;
import com.prykhodko.shop.service.ConfirmationCodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {

    private static final Logger logger = Logger.getLogger(ConfirmationCodeServiceImpl.class);

    private final ConfirmationCodeDao confirmationCodeDao;

    @Autowired
    public ConfirmationCodeServiceImpl(ConfirmationCodeDao confirmationCodeDao) {
        this.confirmationCodeDao = confirmationCodeDao;
    }

    @Override
    public void addConfirmationCode(ConfirmationCode confirmationCode) {
        confirmationCodeDao.addConfirmationCode(confirmationCode);
        logger.info("Added confirm code " + confirmationCode);
    }

    @Override
    public void removeConfirmationCode(Long id) {
        confirmationCodeDao.removeConfirmationCode(id);
        logger.info("Deleted confirm code with id " + id);
    }

    @Override
    public List<ConfirmationCode> getByUserEmail(String userEmail) {
        return confirmationCodeDao.getByUserEmail(userEmail);
    }

    @Override
    public Optional<ConfirmationCode> getLastConfirmationCodeForUser(User user) {
        return confirmationCodeDao.getLastConfirmationCodeForUser(user);
    }
}
