package com.prykhodko.shop.service.impl;

import com.prykhodko.shop.dao.UserDao;
import com.prykhodko.shop.model.User;
import com.prykhodko.shop.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
        logger.info("Added user " + user);
    }

    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
        logger.info("Deleted user with id " + id);
    }

    @Override
    public void updateUser(Long id, User user) {
        userDao.updateUser(id, user);
        logger.info("Updated user with id" + id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        logger.info("Get user with email " + email);
        return userDao.getByEmail(email);
    }

    @Override
    public Optional<User> getById(Long id) {
        logger.info("Get user with id " + id);
        return userDao.getById(id);
    }

    @Override
    public List<User> getAll() {
        logger.info("Get all users");
        return userDao.getAll();
    }
}
