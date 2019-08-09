package com.prykhodko.shop.service;

import com.prykhodko.shop.model.StockOnOrder;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void addOrder(StockOnOrder stockOnOrder);

    void removeOrder(Long id);

    Optional<StockOnOrder> getById(Long id);

    List<StockOnOrder> getAll();
}
