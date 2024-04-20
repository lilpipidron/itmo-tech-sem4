package ru.kramskoi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.mapper.CatMapper;
import ru.kramskoi.service.CatService;

import javax.validation.Valid;
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
    public ResponseEntity<CatDTO> getCatById(@PathVariable("id") Long id) {
        CatDTO catDTO = catService.findCatByID(id);
        if (catDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(catDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CatDTO>> getCatsByColorOrBreed(
            @RequestParam(value = "color", required = false) Color color,
            @RequestParam(value = "breed", required = false) Breed breed) {

        if (color == null && breed == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CatDTO> catDTOList;
        if (breed != null) {
            catDTOList = catService.findCatsByBreed(breed);
        } else {
            catDTOList = catService.findCatsByColor(color);
        }

        return new ResponseEntity<>(catDTOList, HttpStatus.OK);
    }


    @GetMapping("/{id}/friends")
    public ResponseEntity<List<CatDTO>> getFriendsById(@PathVariable("id") Long id) {
        List<CatDTO> catDTOList = catService.getFriendsById(id);
        if (catDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(catDTOList, HttpStatus.OK);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<CatDTO>> getAllCatsByOwnerId(@PathVariable("id") Long id) {
        List<CatDTO> catDTOList = catService.getAllCatsByOwnerId(id);
        if (catDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(catDTOList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCat(@PathVariable("id") Long id, @Valid @RequestBody CatDTO catDTO) {
        CatDTO existingCatDTO = catService.findCatByID(id);
        if (existingCatDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catService.updateCat(CatMapper.fromDTOToCat(catDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{catId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable("catId") Long catId, @PathVariable("friendId") Long friendId) {
        catService.addFriend(catId, friendId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Void> addCat(@Valid @RequestBody CatDTO catDTO) {
        catService.addCat(CatMapper.fromDTOToCat(catDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCat(@PathVariable("id") Long id) {
        CatDTO catDTO = catService.findCatByID(id);
        if (catDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catService.deleteCat(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
