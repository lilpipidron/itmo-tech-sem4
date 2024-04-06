package ru.kramskoi.controller;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.mapper.OwnerMapper;
import ru.kramskoi.service.OwnerService;

@RestController
@RequestMapping("/owner")
@Validated
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/getOwnerById")
    public OwnerDTO getOwnerById(@RequestParam("id") Long id) {
        return ownerService.getOwnerByID(id);
    }

    @PutMapping("/updateOwner")
    public void updateOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.updateOwner(OwnerMapper.fromDTOToOwner(ownerDTO));
    }

    @PostMapping("/addOwner")
    public void addOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.addOwner(OwnerMapper.fromDTOToOwner(ownerDTO));
    }

    @DeleteMapping("/deleteOwner")
    public void deleteOwner(@RequestParam("id") Long id) {
        ownerService.deleteOwner(id);
    }
}
