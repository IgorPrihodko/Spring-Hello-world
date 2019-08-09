package com.prykhodko.shop.dao.impl;

import com.prykhodko.shop.dao.ConfirmationCodeDao;
import com.prykhodko.shop.model.ConfirmationCode;
import com.prykhodko.shop.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ConfirmationCodeDaoHibernate implements ConfirmationCodeDao {

    private static final Logger logger = Logger.getLogger(ConfirmationCodeDaoHibernate.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public ConfirmationCodeDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addConfirmationCode(ConfirmationCode confirmationCode) {
        Session session = sessionFactory.getCurrentSession();
        session.save(confirmationCode);
        logger.warn("Added new confirm code " + confirmationCode);
    }

    @Override
    public void removeConfirmationCode(Long id) {
        Session session = sessionFactory.getCurrentSession();
        ConfirmationCode confirmationCode = session.byId(ConfirmationCode.class).load(id);
        session.delete(confirmationCode);
        logger.warn("deleted confirm code " + id);
    }

    @Override
    public List<ConfirmationCode> getByUserEmail(String userEmail) {
        Session session = sessionFactory.getCurrentSession();
        String query = " FROM ConfirmationCode WHERE user.email = :userEmail";
        return session.createQuery(query).list();
    }

    @Override
    public Optional<ConfirmationCode> getLastConfirmationCodeForUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        String query = "FROM ConfirmationCode WHERE user = :user  ORDER BY id desc";
        ConfirmationCode confirmationCode =
                (ConfirmationCode) session.createQuery(query).list().get(0);
        return Optional.ofNullable(confirmationCode);
    }
}
