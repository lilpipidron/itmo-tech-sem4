package ru.kramskoi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.CatClientDTO;
import ru.kramskoi.dto.Color;
import ru.kramskoi.exception.CatNotFound;
import ru.kramskoi.service.CatService;

import java.util.List;

@RestController
@RequestMapping("/cat")
@Validated
public class CatController {

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CatClientDTO getCatById(@PathVariable("id") Long id) {
        CatClientDTO cat;
        try {
            cat = catService.findCatByID(id);
        } catch (CatNotFound e) {
           throw new CatNotFound();
        }

        return cat;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CatClientDTO> getCatsByColorOrBreed(
            @RequestParam("color") Color color,
            @RequestParam("breed") Breed breed) {
        return catService.getCatsByColorOrBreed(color, breed);
    }


    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<CatClientDTO> getFriendsById(@PathVariable("id") Long id) {
        return catService.getFriendsById(id);
    }

    @GetMapping("/owner/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CatClientDTO> getAllCatsByOwnerId(
            @PathVariable("id") Long id) {
        return catService.getAllCatsByOwnerId(id);
    }
}
