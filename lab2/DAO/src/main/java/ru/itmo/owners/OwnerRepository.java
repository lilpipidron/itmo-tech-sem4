package ru.itmo.owners;

import java.util.UUID;

public interface OwnerRepository {
    void addNewOwner(Owner owner);
    Owner getOwnerById(UUID id);
    void addCat(UUID ownerId, UUID catId);
    void deleteCat(UUID ownerId, UUID catId);
}
