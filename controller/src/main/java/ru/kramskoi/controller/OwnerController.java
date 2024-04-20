package ru.kramskoi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.mapper.OwnerMapper;
import ru.kramskoi.service.OwnerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/owner")
@Validated
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable("id") Long id) {
        OwnerDTO ownerDTO = ownerService.getOwnerByID(id);
        if (ownerDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownerDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOwner(@PathVariable("id") Long id, @Valid @RequestBody OwnerDTO ownerDTO) {
        OwnerDTO existingOwnerDTO = ownerService.getOwnerByID(id);
        if (existingOwnerDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ownerService.updateOwner(OwnerMapper.fromDTOToOwner(ownerDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.addOwner(OwnerMapper.fromDTOToOwner(ownerDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable("id") Long id) {
        OwnerDTO ownerDTO = ownerService.getOwnerByID(id);
        if (ownerDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ownerService.deleteOwner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
