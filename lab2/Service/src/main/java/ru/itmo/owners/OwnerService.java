package ru.itmo.owners;

import java.sql.Date;

public interface OwnerService {
    void addNewOwner(String name, Date birthday);
    Owner getOwnerById(int id);
    void addCat(int ownerId, int catId);
    void deleteCat(int ownerId, int catId);
}
