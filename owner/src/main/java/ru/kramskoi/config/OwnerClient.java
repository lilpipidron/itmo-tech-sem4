package ru.kramskoi.config;

import ru.kramskoi.models.OwnerDTO;

public interface OwnerClient {

    OwnerDTO getOwner(Long id);
}
