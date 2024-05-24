package ru.kramskoi.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.exception.CatNotFound;
import ru.kramskoi.mapper.CatMapper;
import ru.kramskoi.service.CatService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cat")
@Validated
@ControllerAdvice
public class CatController {

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CatDTO getCatById(@PathVariable("id") Long id, Principal principal) {
        return catService.findCatByID(id, principal);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CatDTO> getCatsByColorOrBreed(
            @RequestParam(value = "color", required = false) Color color,
            @RequestParam(value = "breed", required = false) Breed breed,
            Principal principal) {
        return catService.getCatsByColorOrBreed(color, breed, principal);
    }


    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<CatDTO> getFriendsById(@PathVariable("id") Long id, Principal principal) {
        return catService.getFriendsById(id, principal);
    }

    @GetMapping("/owner/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CatDTO> getAllCatsByOwnerId(@PathVariable("id") Long id, Principal principal) {
        return catService.getAllCatsByOwnerId(id, principal);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCat(@PathVariable("id") Long id, @Valid @RequestBody CatDTO catDTO, Principal principal) {
        catService.findCatByID(id, principal);
        catService.updateCat(CatMapper.fromDTOToCat(catDTO), principal);
    }

    @PostMapping("/{catId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFriend(@PathVariable("catId") Long catId, @PathVariable("friendId") Long friendId, Principal principal) {
        catService.addFriend(catId, friendId, principal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCat(@Valid @RequestBody CatDTO catDTO, Principal principal) {
        catService.addCat(CatMapper.fromDTOToCat(catDTO), principal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCat(@PathVariable("id") Long id, Principal principal) {
        catService.findCatByID(id, principal);
        catService.deleteCat(id, principal);
    }

    @ExceptionHandler(CatNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCatException(CatNotFound e) {
        return e.getMessage();
    }
}
