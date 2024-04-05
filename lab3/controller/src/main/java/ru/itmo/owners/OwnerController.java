package ru.itmo.owners;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.cats.Cat;

@RestController
@Validated
@RequestMapping("/owner")
public class OwnerController {
    private final OwnerServiceImpl ownerServiceImpl;

    public OwnerController(OwnerServiceImpl ownerServiceImpl) {
        this.ownerServiceImpl = ownerServiceImpl;
    }

    @GetMapping("/addOwner")
    public void addOwner(@Validated @RequestBody Owner owner) {
        ownerServiceImpl.addOwner(owner);
    }
    @GetMapping("/getOwnerById")
    public OwnerDTO getOwnerById(@RequestParam("id") Long id) {
        return ownerServiceImpl.getOwnerByID(id);
    }
}
