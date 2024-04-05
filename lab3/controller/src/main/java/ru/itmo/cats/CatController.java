package ru.itmo.cats;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.breeds.Breed;
import ru.itmo.colors.Color;

import java.util.List;

@RestController
@Validated
@RequestMapping("/cat")
public class CatController {
    private final CatServiceImpl catServiceImpl;

    public CatController(CatServiceImpl catServiceImpl) {
        this.catServiceImpl = catServiceImpl;
    }

    @GetMapping("/addCat")
    public void addCat(@Validated @RequestBody Cat cat) {
        catServiceImpl.addCat(cat);
    }

    @GetMapping("/findCatByID")
    public CatDTO findCatByID(@RequestParam("id") Long id) {
        return catServiceImpl.findCatByID(id);
    }

    @GetMapping("/findCatsByColor")
    public List<CatDTO> findCatsByColor(@RequestParam("color") Color color) {
        return catServiceImpl.findCatsByColor(color);
    }

    @GetMapping("/findCatsByBreed")
    public List<CatDTO> findCatsByBreed(@RequestParam("breed") Breed breed) {
        return catServiceImpl.findCatsByBreed(breed);
    }

    @GetMapping("/getAllFriends")
    public List<CatDTO> getAllFriends(@RequestParam("id") Long id) {
        return catServiceImpl.getAllFriends(id);
    }

    @GetMapping("/addFriend")
    public void addFriend(@RequestParam("catId") Long catId, @RequestParam("friendId") Long friendId) {
        catServiceImpl.addFriend(catId, friendId);
    }

}
