package ru.itmo.owners;

import ru.itmo.cats.Cat;

import java.util.List;

public interface OwnerRepository {
    void addNewOwner(Owner owner);

    Owner getOwnerById(int id);

    void deleteOwner(int ownerId);

    List<Cat> getAllCatsId(int id);
}
