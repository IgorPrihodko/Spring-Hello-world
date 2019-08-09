package com.prykhodko.shop.utils;

import com.prykhodko.shop.model.Basket;
import com.prykhodko.shop.model.Product;

import java.math.BigDecimal;

public class TotalPriceCounter {

    public static BigDecimal count(Basket basket) {
        BigDecimal totalPrice = BigDecimal.valueOf(0.0);
        for (Product product : basket.getProductsInBasket()) {
            totalPrice = totalPrice.add(product.getPrice());
        }
        return totalPrice;
    }
}
