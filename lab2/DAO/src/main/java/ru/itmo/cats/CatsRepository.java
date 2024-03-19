package ru.itmo.cats;

import java.util.ArrayList;
import java.util.UUID;

public interface CatsRepository {
    void addNewCat(Cat cat, UUID owner);
    Cat getCatById(UUID id);
    ArrayList<Cat> getAllFriends(UUID id);
    void addFriend(UUID catId, UUID friendId);
    void deleteCat(UUID catId, UUID ownerId);
}
