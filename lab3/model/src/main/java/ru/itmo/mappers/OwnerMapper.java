package ru.itmo.mappers;

import lombok.experimental.UtilityClass;
import ru.itmo.owners.Owner;
import ru.itmo.owners.OwnerDTO;

@UtilityClass
public class OwnerMapper {
    public OwnerDTO fromOwnerToDTO(Owner owner) {
        return new OwnerDTO(owner.getId(), owner.getName(), owner.getBirthday());
    }

    public Owner fromDTOToOwner(OwnerDTO ownerDTO) {
        return new Owner(ownerDTO.getId(), ownerDTO.getName(), ownerDTO.getBirthday());
    }
}
