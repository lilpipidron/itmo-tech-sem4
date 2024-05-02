package ru.kramskoi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.exception.OwnerNotFound;
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
    @ResponseStatus(HttpStatus.OK)
    public OwnerDTO getOwnerById(@PathVariable("id") Long id) {
        return ownerService.getOwnerByID(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOwner(@PathVariable("id") Long id, @Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.getOwnerByID(id);
        ownerService.updateOwner(OwnerMapper.fromDTOToOwner(ownerDTO));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOwner(@Valid @RequestBody OwnerDTO ownerDTO, Principal principal) {
        ownerDTO.setId(ownerService.addOwner(OwnerMapper.fromDTOToOwner(ownerDTO)));
        personService.addOwner(principal, ownerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@PathVariable("id") Long id) {
        ownerService.getOwnerByID(id);
        ownerService.deleteOwner(id);
    }

    @ExceptionHandler(OwnerNotFound.class)
    public ResponseEntity<String> handleOwnerException(OwnerNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
