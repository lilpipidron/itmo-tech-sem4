package ru.kramskoi.service;

import ru.kramskoi.models.OwnerDTO;

import java.security.Principal;

public interface OwnerGatewayService {
    Long addOwner(OwnerDTO owner, Principal principal);

    OwnerDTO getOwnerByID(Long id, Principal principal);

    void updateOwner(OwnerDTO owner, Principal principal);

    void deleteOwner(Long id, Principal principal);
}