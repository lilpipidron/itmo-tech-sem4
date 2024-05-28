package ru.kramskoi.service;

import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.Color;
import ru.kramskoi.dto.CatClientDTO;
import ru.kramskoi.dto.CatMessage;
import ru.kramskoi.dto.FriendMessage;

import java.util.List;

public interface CatService {
  void addCat(CatMessage catMessage);

  CatClientDTO findCatByID(Long id);

  List<CatClientDTO> getFriendsById(Long id);

  void addFriend(FriendMessage friend);

  List<CatClientDTO> getAllCatsByOwnerId(Long id);

  List<CatClientDTO> getCatsByColorOrBreed(Color color, Breed breed);

  void updateCat(CatMessage catMessage);

  void deleteCat(CatMessage catMessage);
}
