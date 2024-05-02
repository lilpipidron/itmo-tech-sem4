package ru.kramskoi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.mapper.OwnerMapper;
import ru.kramskoi.service.OwnerService;
import ru.kramskoi.service.PersonService;

import java.security.Principal;

@RestController
@RequestMapping("/owner")
@Validated
@ControllerAdvice
public class OwnerController {

    private final OwnerService ownerService;
    private final PersonService personService;

    public OwnerController(OwnerService ownerService, PersonService personService) {
        this.ownerService = ownerService;
        this.personService = personService;
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
        ownerDTO.setId(ownerService.addOwner(OwnerMapper.fromDTOToOwner(ownerDTO)));
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
