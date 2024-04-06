package ru.itmo.cats;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import ru.itmo.hibernate.HibernateSessionFactoryUtil;

import java.awt.font.TransformAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CatRepositoryImpl implements CatRepository {
    @Override
    public void addNewCat(Cat cat) {
      Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(cat);
        transaction.commit();
        session.close();
    }

    @Override
    public Cat getCatById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Cat cat = session.get(Cat.class, id);
        transaction.commit();
        session.close();
        return cat;
    }

    @Override
    public List<Cat> getAllFriends(int id) {
        Cat cat = getCatById(id);
        return cat.getFriends();
    }


    @Override
    public void addFriend(int catId, int friendId) {
       Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Cat cat = getCatById(catId);
        Cat friend = getCatById(friendId);
        cat.addFriend(friend);
        friend.addFriend(cat);
        session.merge(cat);
        session.merge(friend);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteCat(int catId) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Cat cat = getCatById(catId);
        session.delete(cat);
        transaction.commit();
        session.close();
    }
}
