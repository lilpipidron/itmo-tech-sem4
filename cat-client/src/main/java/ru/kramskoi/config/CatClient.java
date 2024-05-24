package ru.kramskoi.config;

import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.Cat;
import ru.kramskoi.dto.Color;

import java.util.List;

public interface CatClient {
    Cat getCatById(long id);

    List<Cat> getFriendsById(long id);

    List<Cat> getCatsByColorOrBreed(Color color, Breed breed);

    void updateCat(Cat cat);

    void addFriend(long catId, long friendId);

    void addCat(Cat cat);

    List<Cat> getAllCatsByOwnerId(long ownerId);

    void deleteCat(long id);
}
