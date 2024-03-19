package ru.itmo.owners;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;

public class OwnerRepositoryImp implements OwnerRepository {
    private final Session session;

    public OwnerRepositoryImp(Session session) {
        this.session = session;
    }

    @Override
    public void addNewOwner(Owner owner) {
        Transaction transaction = session.beginTransaction();
        session.save(owner);
        transaction.commit();
    }

    @Override
    public Owner getOwnerById(int id) {
        return session.get(Owner.class, id);
    }

    @Override
    public void addCat(int ownerId, int catId) {
        String sql = "INSERT INTO owner_cat (owner_id, cat_id) VALUES (:ownerId, :catId)";
        session.createNativeQuery(sql)
                .setParameter("ownerId", ownerId)
                .setParameter("catId", catId)
                .executeUpdate();
    }

    @Override
    public void deleteOwner(int ownerId) {
        String sql = "DELETE FROM owner_cat WHERE owner_id = :ownerId";
        session.createNativeQuery(sql)
                .setParameter("ownerId", ownerId)
                .executeUpdate();

        sql = "DELETE FROM owners WHERE id = :ownerId";
        session.createNativeQuery(sql)
                .setParameter("ownerId", ownerId)
                .executeUpdate();
    }

    @Override
    public ArrayList<Integer> getAllCatsId(int id) {
        String sql = "SELECT cat_id FROM owner_cat WHERE owner_id = :ownerId";
        NativeQuery<Integer> query = session.createNativeQuery(sql, Integer.class)
                .setParameter("ownerId", id);
        return (ArrayList<Integer>) query.list();
    }
}
