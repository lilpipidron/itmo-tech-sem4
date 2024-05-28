package ru.kramskoi.config;

import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.CatMessage;
import ru.kramskoi.dto.Color;

import java.util.List;

public interface CatClient {
    CatMessage getCatById(long id);

    List<CatMessage> getFriendsById(long id);

    List<CatMessage> getCatsByColorOrBreed(Color color, Breed breed);

    List<CatMessage> getAllCatsByOwnerId(long ownerId);
}
