package ru.itmo.cats;

import ru.itmo.breeds.Breed;
import ru.itmo.colors.Color;

import java.util.List;

public interface CatService {
    void addCat(Cat cat);

    CatDTO findCatByID(long id);

    List<CatDTO> findCatsByColor(Color color);

    List<CatDTO> findCatsByBreed(Breed breed);

    List<CatDTO> getAllFriends(long id);

    void addFriend(long catId, long friendId);

    List<CatDTO> getAllCats(long id);
}
