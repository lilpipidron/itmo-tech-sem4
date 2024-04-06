package ru.kramskoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.entity.Cat;
import ru.kramskoi.mapper.CatMapper;
import ru.kramskoi.repository.CatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(basePackages = {"ru.kramskoi.repository"})
public class CatServiceImpl implements CatService {
  @Autowired
  CatRepository catRepository;

  @Override
  public void addCat(Cat cat) {
    catRepository.save(cat);
  }

  @Override
  public CatDTO findCatByID(Long id) {
    return CatMapper.fromCatToDTO(catRepository.getCatById(id));
  }

  @Override
  public List<CatDTO> findCatsByColor(Color color) {
    List<CatDTO> catDTOS = new ArrayList<>();
    List<Cat> cats = catRepository.getCatsByColor(color);
    for (Cat cat : cats) {
      catDTOS.add(CatMapper.fromCatToDTO(cat));
    }
    return catDTOS;
  }

  @Override
  public List<CatDTO> findCatsByBreed(Breed breed) {
    List<CatDTO> catDTOS = new ArrayList<>();
    List<Cat> cats = catRepository.getCatsByBreed(breed);
    for (Cat cat : cats) {
      catDTOS.add(CatMapper.fromCatToDTO(cat));
    }
    return catDTOS;
  }

  @Override
  public List<CatDTO> getFriendsById(Long id) {
    List<CatDTO> catDTOS = new ArrayList<>();
    List<Cat> cats = catRepository.getFriendsById(id);
    for (Cat cat : cats) {
      catDTOS.add(CatMapper.fromCatToDTO(cat));
    }
    return catDTOS;
  }

  @Override
  public void addFriend(Long catId, Long friendId) {
    Cat cat = catRepository.getCatById(catId);
    Cat friend = catRepository.getCatById(friendId);
    cat.addFriend(friend);
    friend.addFriend(cat);
    catRepository.save(cat);
    catRepository.save(friend);
  }

  @Override
  public List<CatDTO> getAllCatsByOwnerId(Long id) {
    List<CatDTO> catDTOS = new ArrayList<>();
    List<Cat> cats = catRepository.getCatsByOwnerId(id);
    for (Cat cat : cats) {
      catDTOS.add(CatMapper.fromCatToDTO(cat));
    }
    return catDTOS;
  }
}
