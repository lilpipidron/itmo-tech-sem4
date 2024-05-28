package ru.kramskoi.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.*;
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
    public CatClientDTO getCatById(@PathVariable("id") Long id) {
        return catService.findCatByID(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CatClientDTO> getCatsByColorOrBreed(
            @PathVariable("color") Color color,
            @PathVariable("breed") Breed breed) {
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

    @ExceptionHandler(CatNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCatException(CatNotFound e) {
        return e.getMessage();
    }
}
