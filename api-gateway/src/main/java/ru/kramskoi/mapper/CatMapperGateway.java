package ru.kramskoi.mapper;

import lombok.experimental.UtilityClass;
import ru.kramskoi.dto.CatClientDTO;
import ru.kramskoi.dto.CatDTO;

@UtilityClass
public class CatMapperGateway {

    public CatDTO fromCatClientDTOToCatDTO(CatClientDTO cat) {
        if (cat == null) {
            return null;
        }
        return new CatDTO(cat.getId(), cat.getName(), cat.getBirthday(), cat.getBreed(), cat.getColor(), cat.getOwnerId());
    }

    public CatClientDTO fromCatDTOTOToCatClientDTO(CatDTO cat) {
        if (cat == null) {
            return null;
        }
        return new CatClientDTO(cat.getId(), cat.getName(), cat.getBirthday(), cat.getBreed(), cat.getColor(), cat.getOwnerId());
    }
}