package ru.kramskoi.service;

import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.dto.OwnerMessage;
import ru.kramskoi.dto.OwnerPost;

import java.security.Principal;

public interface OwnerGatewayService {
    Long addOwner(OwnerMessage owner, Principal principal);

    OwnerDTO getOwnerByID(Long id, Principal principal);

    void updateOwner(OwnerPost owner, Principal principal);

    void deleteOwner(Long id, Principal principal);
}
