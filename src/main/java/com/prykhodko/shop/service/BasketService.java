package com.prykhodko.shop.service;

import com.prykhodko.shop.model.Basket;
import com.prykhodko.shop.model.User;

import java.util.List;
import java.util.Optional;

public interface BasketService {

    void addBasket(Basket basket);

    void removeBasket(Long id);

    Optional<Basket> getLastBasketForUser(User user);

    List<Basket> getAllByUser(User user);
}
