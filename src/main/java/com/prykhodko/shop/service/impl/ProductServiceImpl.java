package com.prykhodko.shop.service.impl;

import com.prykhodko.shop.dao.ProductDao;
import com.prykhodko.shop.model.Product;
import com.prykhodko.shop.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    @Transactional
    public void addProduct(Product product) {
        productDao.addProduct(product);
        logger.info("Added product " + product);
    }

    @Override
    @Transactional
    public void removeProduct(Long id) {
        productDao.removeProduct(id);
        logger.info("Deleted product with id " + id);
    }

    @Override
    @Transactional
    public void updateProduct(Long id, Product product) {
        productDao.updateProduct(id, product);
        logger.info("Updated product with id" + id);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productDao.getById(id);
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }
}
