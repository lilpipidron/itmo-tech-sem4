package ru.itmo.owners;

import java.util.UUID;

public interface OwnerService {
    void addOwner(Owner owner);

    void addCat(UUID ownerId, UUID catId);
    void deleteCat(UUID ownerId, UUID catId);
}
