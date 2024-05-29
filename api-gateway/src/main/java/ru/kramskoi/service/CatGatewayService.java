package ru.kramskoi.service;

import ru.kramskoi.dto.CatClientDTO;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.dto.FriendMessage;

import java.security.Principal;
import java.util.List;

public interface CatGatewayService {
    void addCat(CatDTO catDTO, Principal principal);

    void deleteCat(long id, Principal principal);

    void updateCat(CatDTO catDTO, Principal principal);

    CatDTO getCat(long id, Principal principal);

    void addFriend(FriendMessage friendMessage, Principal principal);

    List<CatClientDTO> getFriends(long id, Principal principal);
}
