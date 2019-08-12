package com.prykhodko.shop.utils;

import com.prykhodko.shop.model.Basket;
import com.prykhodko.shop.model.Product;
import com.prykhodko.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TotalPriceCounter {

    private final ProductService productService;

    @Autowired
    public TotalPriceCounter(ProductService productService) {
        this.productService = productService;
    }

    public BigDecimal count(Basket basket) {
        BigDecimal totalPrice = BigDecimal.valueOf(0.0);
        for (Product product : basket.getProductsInBasket()) {
            Product productFromDB = productService.getById(product.getId()).get();
            totalPrice = totalPrice.add(productFromDB.getPrice());
        }
        return totalPrice;
    }
}
