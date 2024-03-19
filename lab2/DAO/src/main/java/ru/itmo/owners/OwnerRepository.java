package ru.itmo.owners;

import java.util.ArrayList;

public interface OwnerRepository {
    void addNewOwner(Owner owner);
    Owner getOwnerById(int id);
    void addCat(int ownerId, int catId);
    void deleteOwner(int ownerId);

    ArrayList<Integer> getAllCatsId(int id);
}
