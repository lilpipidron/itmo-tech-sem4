package ru.kramskoi.mapper;

import lombok.experimental.UtilityClass;
import ru.kramskoi.dto.OwnerClientDTO;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Owner;

@UtilityClass
public class OwnerMapperGateway {
    public OwnerDTO fromOwnerClientDTOToDTO(OwnerClientDTO owner) {
        if (owner == null) {
            return null;
        }
        return new OwnerDTO(owner.getId(), owner.getName(), owner.getBirthday(), owner.getPersonID());
    }
}
