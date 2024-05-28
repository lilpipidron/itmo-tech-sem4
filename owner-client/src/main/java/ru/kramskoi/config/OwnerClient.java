package ru.kramskoi.config;

import ru.kramskoi.dto.OwnerMessage;

public interface OwnerClient {

    OwnerMessage getOwner(Long id);
}
