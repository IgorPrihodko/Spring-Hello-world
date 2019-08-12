package com.prykhodko.shop.dao;

import com.prykhodko.shop.model.Basket;
import com.prykhodko.shop.model.User;

import java.util.List;
import java.util.Optional;

public interface BasketDao {

    void addBasket(Basket basket);

    void removeBasket(Long id);

    Optional<Basket> getLastBasketForUser(User user);

    List<Basket> getAllByUser(User user);
}
