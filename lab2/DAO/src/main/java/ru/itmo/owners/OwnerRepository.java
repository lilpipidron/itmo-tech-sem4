package ru.itmo.owners;

import java.util.List;

public interface OwnerRepository {
    void addNewOwner(Owner owner);

    Owner getOwnerById(int id);

    void addCat(int ownerId, int catId);

    void deleteOwner(int ownerId);

    List<Integer> getAllCatsId(int id);
}
