package ru.kramskoi.mapper;

import lombok.experimental.UtilityClass;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Owner;

@UtilityClass

public class OwnerMapper {

    public OwnerDTO fromOwnerToDTO(Owner owner) {
        if (owner == null){
            return null;
        }
        return new OwnerDTO(owner.getId(), owner.getName(), owner.getBirthday());
    }

    public Owner fromDTOToOwner(OwnerDTO ownerDTO) {
        if (ownerDTO == null){
            return null;
        }
        return new Owner(ownerDTO.getId(), ownerDTO.getName(), ownerDTO.getBirthday());
    }
}
