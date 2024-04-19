package ru.kramskoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.mapper.CatMapper;
import ru.kramskoi.service.CatService;

import java.util.List;

@RestController
@RequestMapping("/cat")
@Validated

public class CatController {
  @Autowired
  private final CatService catService;

  public CatController(CatService catService) {
    this.catService = catService;
  }

  @GetMapping("/findCatByID")
  public CatDTO findCatByID(@RequestParam("id") Long id) {
    return catService.findCatByID(id);
  }

  @GetMapping("/findCatsByColor")
  public List<CatDTO> findCatsByColor(@RequestParam("color") Color color) {
    return catService.findCatsByColor(color);
  }

  @GetMapping("/findCatsByBreed")
  public List<CatDTO> findCatsByBreed(@RequestParam("breed") Breed breed) {
    return catService.findCatsByBreed(breed);
  }

  @GetMapping("/getAllFriends")
  public List<CatDTO> getAllFriends(@RequestParam("id") Long id) {
    return catService.getFriendsById(id);
  }

  @GetMapping("/getAllCatsByOwnerId")
  public List<CatDTO> getAllCatsByOwnerId(@RequestParam("id") Long id) {
    return catService.getAllCatsByOwnerId(id);
  }

  @PutMapping("/updateCat")
  public void updateCat(@Validated @RequestBody CatDTO catDTO) {
    catService.updateCat(CatMapper.fromDTOToCat(catDTO));
  }
  @PostMapping("/addFriend")
  public void addFriend(@RequestParam("catId") Long catId, @RequestParam("friendId") Long friendId) {
    catService.addFriend(catId, friendId);
  }

  @PostMapping("/addCat")
  public void addCat(@Validated @RequestBody CatDTO catDTO) {
    catService.addCat(CatMapper.fromDTOToCat(catDTO));
  }

  @DeleteMapping("/deleteCat")
  public void deleteCat(@RequestParam("id") Long id) {
    catService.deleteCat(id);
  }

}
