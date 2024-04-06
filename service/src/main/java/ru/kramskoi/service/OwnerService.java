package ru.kramskoi.service;

import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Owner;

public interface OwnerService {
    void addOwner(Owner owner);

    OwnerDTO getOwnerByID(Long id);

    void updateOwner(Owner owner);

    void deleteOwner(Long id);
}
