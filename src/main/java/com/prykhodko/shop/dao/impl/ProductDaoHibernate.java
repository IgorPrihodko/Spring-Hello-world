package com.prykhodko.shop.dao.impl;

import com.prykhodko.shop.dao.ProductDao;
import com.prykhodko.shop.model.Product;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoHibernate implements ProductDao {

    private static final Logger logger = Logger.getLogger(ProductDaoHibernate.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public ProductDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.save(product);
        logger.info("Added new product " + product);
    }

    @Override
    public void removeProduct(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.byId(Product.class).load(id);
        session.delete(product);
        logger.info("deleted product " + id);
    }

    @Override
    public void updateProduct(Long id, Product product) {
        Session session = sessionFactory.getCurrentSession();
        Product productFromDB = session.byId(Product.class).load(id);
        productFromDB.setTitle(product.getTitle());
        productFromDB.setDescription(product.getDescription());
        productFromDB.setPrice(product.getPrice());
        logger.info("updated product " + id );
    }

    @Override
    public Optional<Product> getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.byId(Product.class).loadOptional(id);
    }

    @Override
    public List<Product> getAll() {
        Session session = sessionFactory.getCurrentSession();
        String query = "FROM Product";
        return session.createQuery(query, Product.class).list();
    }
}
