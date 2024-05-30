package ru.kramskoi.service;

import ru.kramskoi.dto.*;

import java.util.List;
import java.util.Optional;

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
