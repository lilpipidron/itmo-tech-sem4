package ru.kramskoi.config;

import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.CatClientDTO;
import ru.kramskoi.dto.Color;

import java.util.List;
import java.util.Optional;

public interface CatClient {
    CatClientDTO getCatById(Long id);

    List<CatClientDTO> getFriendsById(Long id);

    List<CatClientDTO> getCatsByColorOrBreedAndOwnerId(Color color, Breed breed, Long ownerId);

    List<CatClientDTO> getAllCatsByOwnerId(Long ownerId);
}
