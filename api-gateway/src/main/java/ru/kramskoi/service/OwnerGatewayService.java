package ru.kramskoi.service;

import ru.kramskoi.dto.OwnerClientDTO;
import ru.kramskoi.dto.OwnerDTO;

import java.security.Principal;

public interface OwnerGatewayService {
    Long addOwner(OwnerDTO owner, Principal principal);

    OwnerClientDTO getOwnerByID(Long id, Principal principal);

    void updateOwner(OwnerDTO owner, Principal principal);

    void deleteOwner(Long id, Principal principal);
}