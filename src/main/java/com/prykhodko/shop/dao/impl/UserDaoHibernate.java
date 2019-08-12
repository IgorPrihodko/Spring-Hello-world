package com.prykhodko.shop.dao.impl;

import com.prykhodko.shop.dao.UserDao;
import com.prykhodko.shop.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoHibernate implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoHibernate.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        logger.info("added new user " + user);
    }

    @Override
    public void removeUser(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.byId(User.class).load(id);
        session.delete(user);
        logger.info("deleted user " + id);
    }

    @Override
    public void updateUser(Long id, User user) {
        Session session = sessionFactory.getCurrentSession();
        User userFromDB = session.byId(User.class).load(id);
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPassword(user.getPassword());
        userFromDB.setRole(user.getRole());
        session.flush();
        logger.info("updated user " + id );
    }

    @Override
    public Optional<User> getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User user WHERE user.email = :email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        Optional<User> optionalUser = query.uniqueResultOptional();
        return optionalUser;
    }

    @Override
    public Optional<User> getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.byId(User.class).loadOptional(id);
    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        String query = "FROM User";
        return session.createQuery(query).list();
    }
}
