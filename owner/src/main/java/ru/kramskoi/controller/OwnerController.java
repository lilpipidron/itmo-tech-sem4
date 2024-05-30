package ru.kramskoi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.OwnerClientDTO;
import ru.kramskoi.exception.OwnerNotFound;
import ru.kramskoi.service.OwnerService;

@RestController
@RequestMapping("/owner")
@Validated
public class OwnerController {

    private final OwnerService ownerService;
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OwnerClientDTO getOwnerById(@PathVariable("id") Long id) {
        return ownerService.getOwnerByID(id);
    }
}
