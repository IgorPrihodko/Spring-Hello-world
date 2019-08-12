package com.prykhodko.shop.dao.impl;

import com.prykhodko.shop.dao.OrderDao;
import com.prykhodko.shop.model.StockOnOrder;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoHibernate implements OrderDao {

    private static final Logger logger = Logger.getLogger(OrderDaoHibernate.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public OrderDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addOrder(StockOnOrder stockOnOrder) {
        Session session = sessionFactory.getCurrentSession();
        session.save(stockOnOrder);
        logger.info("added new order " + stockOnOrder);
    }

    @Override
    public void removeOrder(Long id) {
        Session session = sessionFactory.getCurrentSession();
        StockOnOrder order = session.byId(StockOnOrder.class).load(id);
        session.delete(order);
        logger.info("deleted order " + id);
    }

    @Override
    public Optional<StockOnOrder> getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.byId(StockOnOrder.class).loadOptional(id);
    }

    @Override
    public List<StockOnOrder> getAll() {
        Session session = sessionFactory.getCurrentSession();
        String query = "FROM StockOnOrder";
        return session.createQuery(query, StockOnOrder.class).list();
    }
}
