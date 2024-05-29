package ru.kramskoi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.dto.OwnerMessage;
import ru.kramskoi.mapper.OwnerMapperGateway;
import ru.kramskoi.service.OwnerService;
import ru.kramskoi.service.PersonService;

import java.security.Principal;

@RestController
@RequestMapping("/owner")
@Validated
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
        return OwnerMapperGateway.fromOwnerClientDTOToDTO(ownerService.getOwnerByID(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.updateOwner(new OwnerMessage(
                ownerDTO.getId(),
                ownerDTO.getName(),
                ownerDTO.getBirthday(),
                ownerDTO.getPersonId()
        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOwner(@Valid @RequestBody OwnerDTO ownerDTO, Principal principal) {
        ownerDTO.setId(ownerService.addOwner(new OwnerMessage(
                ownerDTO.getId(),
                ownerDTO.getName(),
                ownerDTO.getBirthday(),
                ownerDTO.getPersonId()
        )));
        personService.addOwner(principal, ownerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@PathVariable("id") Long id) {
        ownerService.getOwnerByID(id);
        ownerService.deleteOwner(id);
    }
}
