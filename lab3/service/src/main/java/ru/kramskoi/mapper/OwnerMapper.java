package ru.kramskoi.mapper;

import lombok.experimental.UtilityClass;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Owner;

import java.sql.Date;

@UtilityClass

public class OwnerMapper {

    public OwnerDTO fromOwnerToDTO(Owner owner) {
        return new OwnerDTO(owner.getId(), owner.getName(), owner.getBirthday().toString());
    }

    public Owner fromDTOToOwner(OwnerDTO ownerDTO) {
        return new Owner(ownerDTO.getId(), ownerDTO.getName(), Date.valueOf(ownerDTO.getBirthday()));
    }
}
