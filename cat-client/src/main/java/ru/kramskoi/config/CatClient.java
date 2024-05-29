package ru.kramskoi.config;

import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.CatClientDTO;
import ru.kramskoi.dto.Color;

import java.util.List;

public interface CatClient {
    CatClientDTO getCatById(long id);

    List<CatClientDTO> getFriendsById(long id);

    List<CatClientDTO> getCatsByColorOrBreed(Color color, Breed breed);

    List<CatClientDTO> getAllCatsByOwnerId(long ownerId);
}
