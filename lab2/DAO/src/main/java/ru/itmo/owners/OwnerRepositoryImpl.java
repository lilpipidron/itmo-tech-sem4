package ru.itmo.owners;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.itmo.hibernate.HibernateSessionFactoryUtil;

import java.util.List;

public class OwnerRepositoryImpl implements OwnerRepository {

    @Override
    public void addNewOwner(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public Owner getOwnerById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = session.get(Owner.class, id);
        transaction.commit();
        session.close();
        return owner;
    }

    @Override
    public void deleteOwner(int ownerId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = getOwnerById(ownerId);
        session.delete(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public List getAllCatsId(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "SELECT id FROM cats WHERE owner_id = :ownerId";
        Query query = session.createSQLQuery(sql)
                .setParameter("ownerId", id);
        session.close();
        return query.list();

    }
}
