package com.prykhodko.shop.service.impl;

import com.prykhodko.shop.dao.BasketDao;
import com.prykhodko.shop.model.Basket;
import com.prykhodko.shop.model.User;
import com.prykhodko.shop.service.BasketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    private static final Logger logger = Logger.getLogger(BasketServiceImpl.class);

    private final BasketDao basketDao;

    @Autowired
    public BasketServiceImpl(BasketDao basketDao) {
        this.basketDao = basketDao;
    }

    @Override
    public void addBasket(Basket basket) {
        basketDao.addBasket(basket);
        logger.info("Added basket " + basket);
    }

    @Override
    public void removeBasket(Long id) {
        basketDao.removeBasket(id);
        logger.info("Deleted basket with id " + id);
    }

    @Override
    public Optional<Basket> getLastBasketForUser(User user) {
        return basketDao.getLastBasketForUser(user);
    }

    @Override
    public List<Basket> getAllByUser(User user) {
        return basketDao.getAllByUser(user);
    }
}
