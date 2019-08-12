package com.prykhodko.shop.dao.impl;

import com.prykhodko.shop.dao.BasketDao;
import com.prykhodko.shop.model.Basket;
import com.prykhodko.shop.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BasketDaoHibernate implements BasketDao {

    private static final Logger logger = Logger.getLogger(BasketDaoHibernate.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public BasketDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBasket(Basket basket) {
        Session session = sessionFactory.getCurrentSession();
        session.save(basket);
        logger.info("added new basket " + basket);
    }

    @Override
    public void removeBasket(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Basket basket = session.byId(Basket.class).load(id);
        session.delete(basket);
        logger.info("deleted basket " + id);
    }

    @Override
    public Optional<Basket> getLastBasketForUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        String query = "FROM Basket WHERE user = :user  ORDER BY id desc";
        Basket basket = (Basket) session.createQuery(query).list().get(0);
        return Optional.ofNullable(basket);
    }

    @Override
    public List<Basket> getAllByUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        String query = " FROM Basket WHERE user = :user";
        return session.createQuery(query).list();
    }
}
