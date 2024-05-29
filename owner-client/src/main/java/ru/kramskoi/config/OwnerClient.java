package ru.kramskoi.config;

import ru.kramskoi.dto.OwnerClientDTO;
import ru.kramskoi.dto.OwnerMessage;

public interface OwnerClient {

    OwnerClientDTO getOwner(Long id);
}
