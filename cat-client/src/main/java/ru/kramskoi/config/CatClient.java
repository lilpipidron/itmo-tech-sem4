package ru.kramskoi.config;

import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.CatClientDTO;
import ru.kramskoi.dto.Color;

import java.util.List;
import java.util.Optional;

public interface CatClient {
    Optional<CatClientDTO> getCatById(Long id);

    Optional<List<CatClientDTO>> getFriendsById(Long id);

    Optional<List<CatClientDTO>> getCatsByColorOrBreedAndOwnerId(Color color, Breed breed, Long ownerId);

    Optional<List<CatClientDTO>> getAllCatsByOwnerId(Long ownerId);
}
