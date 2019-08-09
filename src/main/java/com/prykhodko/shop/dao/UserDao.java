package com.prykhodko.shop.dao;

import com.prykhodko.shop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    void addUser(User user);

    void removeUser(Long id);

    void updateUser(Long id, User user);

    Optional<User> getByEmail(String email);

    Optional<User> getById(Long id);

    List<User> getAll();
}
