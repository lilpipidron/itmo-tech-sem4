package ru.kramskoi.config;

import ru.kramskoi.dto.OwnerClientDTO;

public interface OwnerClient {

    OwnerClientDTO getOwner(Long id);
}
