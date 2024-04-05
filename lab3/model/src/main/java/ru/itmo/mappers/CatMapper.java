package ru.itmo.mappers;

import lombok.experimental.UtilityClass;
import ru.itmo.cats.Cat;
import ru.itmo.cats.CatDTO;

import java.util.ArrayList;
import java.util.Optional;

@UtilityClass
public class CatMapper {
    public CatDTO fromCatToDTO(Optional<Cat> cat) {
        return new CatDTO(cat.get().getId(), cat.get().getName(), cat.get().getBirthday(), cat.get().getBreed(), cat.get().getColor(), OwnerMapper.fromOwnerToDTO(cat.get().getOwner()));
    }

    public Cat fromDTOToCat(CatDTO catDTO) {
        return new Cat(catDTO.getId(), catDTO.getName(), catDTO.getBirthday(), catDTO.getBreed(), catDTO.getColor(), new ArrayList<>(), OwnerMapper.fromDTOToOwner(catDTO.getOwner()));
    }
}
