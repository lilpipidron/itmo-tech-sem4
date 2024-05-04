package ru.kramskoi.service;

import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Person;

import java.security.Principal;

public interface PersonService {
    void addPerson(Person person);

    void addOwner(Principal principal, OwnerDTO ownerDTO);
}
