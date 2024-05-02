package ru.kramskoi.service;

import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Owner;

import java.security.Principal;

public interface OwnerService {
    Long addOwner(Owner owner);

    OwnerDTO getOwnerByID(Long id);

    void updateOwner(Owner owner);

    void deleteOwner(Long id);
}
