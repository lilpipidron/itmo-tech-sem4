package ru.kramskoi.service;

import ru.kramskoi.dto.FriendMessage;
import ru.kramskoi.models.Breed;
import ru.kramskoi.models.CatDTO;
import ru.kramskoi.models.Color;

import java.util.List;

public interface CatService {
  void addCat(CatDTO catMessage);

  CatDTO findCatByID(Long id);

  List<CatDTO> getFriendsById(Long id);

  void addFriend(FriendMessage friend);

  List<CatDTO> getAllCatsByOwnerId(Long id);

  List<CatDTO> getCatsByColorOrBreed(Color color, Breed breed);

  void updateCat(CatDTO catMessage);

  void deleteCat(CatDTO catMessage);
}
