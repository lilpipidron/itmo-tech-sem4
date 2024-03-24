package ru.itmo.owners;

import java.util.List;

public interface OwnerService {
    void addNewOwner(String name, String birthday);

    Owner getOwnerById(int id);

    void addCat(int ownerId, int catId);

    void deleteOwner(int ownerId);

    List<Integer> getAllCatsId(int ownerId);
}
