package ru.kramskoi.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Owner;
import ru.kramskoi.service.OwnerService;

@RestController
@RequestMapping("/owner")
@Validated
public class OwnerController {
  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @PostMapping("/addOwner")
  public void addOwner(@Validated @RequestBody Owner owner) {
    ownerService.addOwner(owner);
  }

  @GetMapping("/getOwnerById")
  public OwnerDTO getOwnerById(@RequestParam("id") Long id) {
    return ownerService.getOwnerByID(id);
  }
}
