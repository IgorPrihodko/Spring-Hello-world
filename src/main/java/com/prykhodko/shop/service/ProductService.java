package com.prykhodko.shop.service;

import com.prykhodko.shop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void addProduct(Product product);

    void removeProduct(Long id);

    void updateProduct(Long id, Product product);

    Optional<Product> getById(Long id);

    List<Product> getAll();
}
