package ru.itmo.cats;

import ru.itmo.breeds.Breed;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public interface CatService {
    void addNewCat(int ownerId, String name, String birthday, String breed, String Color);

    Cat getCatById(int id);

    List<Cat> getAllFriends(int id);

    void addFriend(int catId, int friendId);

    void deleteCat(int catId, int ownerId);
}
