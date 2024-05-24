package ru.kramskoi.config;

import ru.kramskoi.dto.OwnerMessage;

public interface OwnerClient {
    Long addOwner(OwnerMessage owner);

    OwnerMessage getOwner(Long id);

    void updateOwner(OwnerMessage ownerMessage);

    void deleteOwner(Long id);
}
