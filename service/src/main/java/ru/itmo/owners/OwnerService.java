package ru.itmo.owners;

import java.util.List;

public interface OwnerService {
    void addNewOwner(String name, String birthday);

    Owner getOwnerById(int id);

    void deleteOwner(int ownerId);

    List getAllCatsId(int ownerId);
}
