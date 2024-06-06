package ru.kramskoi.config;

import ru.kramskoi.models.Breed;
import ru.kramskoi.models.CatDTO;
import ru.kramskoi.models.Color;

import java.util.List;

public interface CatClient {
    CatDTO getCatById(Long id);

    List<CatDTO> getFriendsById(Long id);

    List<CatDTO> getCatsByColorOrBreedAndOwnerId(Color color, Breed breed, Long ownerId);

    List<CatDTO> getAllCatsByOwnerId(Long ownerId);
}
