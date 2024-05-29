package ru.kramskoi.config;

import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.CatClientDTO;
import ru.kramskoi.dto.Color;

import java.util.List;

public interface CatClient {
    CatClientDTO getCatById(long id);

    List<CatClientDTO> getFriendsById(long id);

    List<CatClientDTO> getCatsByColorOrBreedAndOwnerId(Color color, Breed breed, long ownerId);

    List<CatClientDTO> getAllCatsByOwnerId(long ownerId);
}
