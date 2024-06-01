package ru.kramskoi.service;

import ru.kramskoi.models.OwnerDTO;

public interface OwnerService {
    Long addOwner(OwnerDTO owner);

    OwnerDTO getOwnerByID(Long id);

    void updateOwner(OwnerDTO owner);

    void deleteOwner(Long id);
}
