package ru.kramskoi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.models.OwnerDTO;
import ru.kramskoi.service.OwnerGatewayService;
import ru.kramskoi.service.PersonService;

import java.security.Principal;

@RestController
@RequestMapping("/owner")
@Validated
public class OwnerController {

    private final OwnerGatewayService ownerService;
    private final PersonService personService;

    public OwnerController(OwnerGatewayService ownerService, PersonService personService) {
        this.ownerService = ownerService;
        this.personService = personService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OwnerDTO getOwnerById(@PathVariable("id") Long id, Principal principal) {
        return ownerService.getOwnerByID(id, principal);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateOwner(@Valid @RequestBody OwnerDTO ownerDTO, Principal principal) {
        ownerService.updateOwner(ownerDTO, principal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOwner(@Valid @RequestBody OwnerDTO ownerDTO, Principal principal) {
        ownerDTO.setId(ownerService.addOwner(ownerDTO, principal));
        personService.addOwner(principal, ownerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@PathVariable("id") Long id, Principal principal) {
        ownerService.getOwnerByID(id, principal);
        ownerService.deleteOwner(id, principal);
    }
}
