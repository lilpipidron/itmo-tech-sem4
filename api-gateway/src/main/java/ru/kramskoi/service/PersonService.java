package ru.kramskoi.service;

import ru.kramskoi.entity.Person;
import ru.kramskoi.models.OwnerDTO;

import java.security.Principal;

public interface PersonService {
    void addPerson(Person person);

    void addOwner(Principal principal, OwnerDTO ownerDTO);
}
