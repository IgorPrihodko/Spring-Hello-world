package com.prykhodko.shop.service.impl;


import com.prykhodko.shop.dao.OrderDao;
import com.prykhodko.shop.model.StockOnOrder;
import com.prykhodko.shop.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void addOrder(StockOnOrder stockOnOrder) {
        orderDao.addOrder(stockOnOrder);
        logger.info("Added order " + stockOnOrder);
    }

    @Override
    public void removeOrder(Long id) {
        orderDao.removeOrder(id);
        logger.info("Deleted order with id " + id);
    }

    @Override
    public Optional<StockOnOrder> getById(Long id) {
        return orderDao.getById(id);
    }

    @Override
    public List<StockOnOrder> getAll() {
        return orderDao.getAll();
    }
}
