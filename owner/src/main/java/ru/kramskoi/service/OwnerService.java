package ru.kramskoi.service;

import ru.kramskoi.dto.OwnerClientDTO;
import ru.kramskoi.dto.OwnerMessage;

public interface OwnerService {
    Long addOwner(OwnerMessage owner);

    OwnerClientDTO getOwnerByID(Long id);

    void updateOwner(OwnerMessage owner);

    void deleteOwner(Long id);
}
