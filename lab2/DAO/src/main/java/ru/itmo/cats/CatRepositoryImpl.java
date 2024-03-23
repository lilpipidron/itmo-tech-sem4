package ru.itmo.cats;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CatRepositoryImpl implements CatRepository {
    private final Session session;

    public CatRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addNewCat(Cat cat, int ownerId) {
        Serializable catId = session.save(cat);

        String sql = "INSERT INTO owner_cat (owner_id, cat_id) VALUES (:ownerId, :catId)";
        session.createNativeQuery(sql)
                .setParameter("ownerId", ownerId)
                .setParameter("catId", catId)
                .executeUpdate();
    }

    @Override
    public Cat getCatById(int id) {
        return session.get(Cat.class, id);
    }

    @Override
    public List<Cat> getAllFriends(int id) {
        List<Cat> friends = new ArrayList<>();
        String sql = "SELECT friend_id FROM cat_friend WHERE cat_id = :catId";
        NativeQuery<Integer> query = session.createNativeQuery(sql, Integer.class)
                .setParameter("catId", id);
        List<Integer> friendsId = query.list();

        for (Integer friendId : friendsId) {
            friends.add(getCatById(friendId));
        }

        return friends;
    }


    @Override
    public void addFriend(int catId, int friendId) {
        String sql = "INSERT INTO cat_friend (cat_id, friend_id) VALUES (:catId, :friendId)";
        session.createNativeQuery(sql)
                .setParameter("catId", catId)
                .setParameter("friendId", friendId)
                .executeUpdate();
        session.createNativeQuery(sql)
                .setParameter("catId", friendId)
                .setParameter("friendId", catId)
                .executeUpdate();
    }

    @Override
    public void deleteCat(int catId, int ownerId) {
        String sql = "DELETE FROM owner_cat WHERE owner_id = :ownerId and cat_id = :catId";
        session.createNativeQuery(sql)
                .setParameter("ownerId", ownerId)
                .setParameter("catId", catId)
                .executeUpdate();

        sql = "DELETE FROM cat_friend where cat_id = :catId";
        session.createNativeQuery(sql)
                .setParameter("catId", catId)
                .executeUpdate();

        sql = "DELETE FROM cat_friend where friend_id = :catId";
        session.createNativeQuery(sql)
                .setParameter("catId", catId)
                .executeUpdate();


        sql = "DELETE FROM cats where id = :catId";
        session.createNativeQuery(sql)
                .setParameter("catId", catId);
    }
}
