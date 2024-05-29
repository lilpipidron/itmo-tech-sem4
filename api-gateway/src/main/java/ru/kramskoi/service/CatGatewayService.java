package ru.kramskoi.service;

import ru.kramskoi.dto.*;

import java.security.Principal;
import java.util.List;

public interface CatGatewayService {
    void addCat(CatDTO catDTO, Principal principal);

    void deleteCat(long id, Principal principal);

    void updateCat(CatDTO catDTO, Principal principal);

    CatDTO getCat(long id, Principal principal);

    void addFriend(FriendMessage friendMessage, Principal principal);

    List<CatClientDTO> getFriends(long id, Principal principal);

    List<CatClientDTO> getAllCatsByOwnerId(long id, Principal principal);

    List<CatClientDTO> getCatsByColorOrBreed(Color color, Breed breed, Long ownerID, Principal principal);
}
