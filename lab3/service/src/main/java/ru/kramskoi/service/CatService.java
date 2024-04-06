package ru.kramskoi.service;

import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.entity.Cat;

import java.util.List;

public interface CatService {
  void addCat(Cat cat);

  CatDTO findCatByID(Long id);

  List<CatDTO> findCatsByColor(Color color);

  List<CatDTO> findCatsByBreed(Breed breed);

  List<CatDTO> getAllFriends(Long id);

  void addFriend(Long catId, Long friendId);

  List<CatDTO> getAllCatsByOwnerId(Long id);
}
