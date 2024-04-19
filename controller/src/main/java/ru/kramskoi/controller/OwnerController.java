package ru.kramskoi.controller;


import org.springframework.http.HttpStatus;
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

    @GetMapping("/getOwnerById")
    @ResponseStatus(HttpStatus.OK)
    public OwnerDTO getOwnerById(@RequestParam("id") Long id) {
        return ownerService.getOwnerByID(id);
    }

    @PutMapping("/updateOwner")
    @ResponseStatus(HttpStatus.OK)
    public void updateOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.updateOwner(OwnerMapper.fromDTOToOwner(ownerDTO));
    }

    @PostMapping("/addOwner")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.addOwner(OwnerMapper.fromDTOToOwner(ownerDTO));
    }

    @DeleteMapping("/deleteOwner")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@RequestParam("id") Long id) {
        ownerService.deleteOwner(id);
    }
}
