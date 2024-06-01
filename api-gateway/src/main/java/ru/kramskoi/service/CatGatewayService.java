package ru.kramskoi.service;

import ru.kramskoi.dto.FriendMessage;
import ru.kramskoi.models.Breed;
import ru.kramskoi.models.CatDTO;
import ru.kramskoi.models.Color;

import java.security.Principal;
import java.util.List;

public interface CatGatewayService {
    void addCat(CatDTO catDTO, Principal principal);

    void deleteCat(Long id, Principal principal);

    void updateCat(CatDTO catDTO, Principal principal);

    CatDTO getCat(Long id, Principal principal);

    void addFriend(FriendMessage friendMessage, Principal principal);

    List<CatDTO> getFriends(Long id, Principal principal);

    List<CatDTO> getAllCatsByOwnerId(Long id, Principal principal);

    List<CatDTO> getCatsByColorOrBreed(Color color, Breed breed, Long ownerID, Principal principal);
}
