package ru.kramskoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.entity.Cat;
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

  @GetMapping("/addCat")
  public void addCat(@Validated @RequestBody Cat cat) {
    catService.addCat(cat);
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
    return catService.getAllFriends(id);
  }

  @GetMapping("/addFriend")
  public void addFriend(@RequestParam("catId") Long catId, @RequestParam("friendId") Long friendId) {
    catService.addFriend(catId, friendId);
  }

  @GetMapping("/getAllCatsByOwnerId")
  public List<CatDTO> getAllCatsByOwnerId(@RequestParam("id") Long id) {
    return catService.getAllCatsByOwnerId(id);
  }
}
