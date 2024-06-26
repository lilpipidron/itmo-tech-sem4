package ru.kramskoi.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.FriendMessage;
import ru.kramskoi.models.Breed;
import ru.kramskoi.models.CatDTO;
import ru.kramskoi.models.Color;
import ru.kramskoi.service.CatGatewayService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cat")
@Validated
public class CatController {

    private final CatGatewayService catGatewayService;

    public CatController(CatGatewayService catService) {
        this.catGatewayService = catService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CatDTO getCatById(@PathVariable("id") Long id, Principal principal) {
        return  catGatewayService.getCat(id, principal);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CatDTO> getCatsByColorOrBreed(
            @RequestParam(value = "color", required = false) Color color,
            @RequestParam(value = "breed", required = false) Breed breed,
            @RequestParam(value = "ownerID", required = false) Long ownerID,
            Principal principal) {
        return catGatewayService.getCatsByColorOrBreed(color, breed, ownerID, principal);
    }


    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<CatDTO> getFriendsById(@PathVariable("id") Long id, Principal principal) {
        return catGatewayService.getFriends(id, principal);
    }

    @GetMapping("/owner/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CatDTO> getAllCatsByOwnerId(@PathVariable("id") Long id, Principal principal) {
        return catGatewayService.getAllCatsByOwnerId(id, principal);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCat(@Valid @RequestBody CatDTO catDTO, Principal principal) {
        catGatewayService.updateCat(catDTO, principal);
    }

    @PostMapping("/{catId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFriend(@PathVariable("catId") Long catId, @PathVariable("friendId") Long friendId, Principal principal) {
        catGatewayService.addFriend(new FriendMessage(catId, friendId), principal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCat(@Valid @RequestBody CatDTO catDTO, Principal principal) {
        catGatewayService.addCat(catDTO, principal);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCat(@PathVariable("id") Long id, Principal principal) {
        catGatewayService.deleteCat(id, principal);
    }
}
