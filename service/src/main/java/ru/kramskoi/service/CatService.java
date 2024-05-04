package ru.kramskoi.service;

import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.entity.Cat;

import java.security.Principal;
import java.util.List;

public interface CatService {
  void addCat(Cat cat, Principal principal);

  CatDTO findCatByID(Long id, Principal principal);

  List<CatDTO> getFriendsById(Long id, Principal principal);

  void addFriend(Long catId, Long friendId, Principal principal);

  List<CatDTO> getAllCatsByOwnerId(Long id, Principal principal);

  List<CatDTO> getCatsByColorOrBreed(Color color, Breed breed, Principal principal);

  void updateCat(Cat cat, Principal principal);

  void deleteCat(Long id, Principal principal);
}
