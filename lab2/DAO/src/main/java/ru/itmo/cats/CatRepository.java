package ru.itmo.cats;

import java.util.ArrayList;

public interface CatRepository {
    void addNewCat(Cat cat, int owner);
    Cat getCatById(int id);
    ArrayList<Cat> getAllFriends(int id);
    void addFriend(int catId, int friendId);
    void deleteCat(int catId, int ownerId);
}
