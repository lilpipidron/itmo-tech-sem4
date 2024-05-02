package ru.kramskoi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Owner;
import ru.kramskoi.mapper.OwnerMapper;
import ru.kramskoi.repository.OwnerRepository;
import ru.kramskoi.service.OwnerService;

import jakarta.validation.Valid;
import ru.kramskoi.service.PersonService;

import java.security.Principal;

@RestController
@RequestMapping("/owner")
@Validated
public class OwnerController {

    private final OwnerService ownerService;
    private final PersonService personService;
    private final OwnerRepository ownerRepository;

    public OwnerController(OwnerService ownerService, PersonService personService,
                           OwnerRepository ownerRepository) {
        this.ownerService = ownerService;
        this.personService = personService;
        this.ownerRepository = ownerRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable("id") Long id) {
        OwnerDTO ownerDTO;
        try {
            ownerDTO = ownerService.getOwnerByID(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownerDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOwner(@PathVariable("id") Long id, @Valid @RequestBody OwnerDTO ownerDTO) {
        try {
            ownerService.getOwnerByID(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ownerService.updateOwner(OwnerMapper.fromDTOToOwner(ownerDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addOwner(@Valid @RequestBody OwnerDTO ownerDTO, Principal principal) {
        ownerService.addOwner(OwnerMapper.fromDTOToOwner(ownerDTO));
        personService.addOwner(principal, ownerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable("id") Long id) {
        try {
            ownerService.getOwnerByID(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ownerService.deleteOwner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
