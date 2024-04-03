package ru.itmo.mappers;

import ru.itmo.cats.Cat;
import ru.itmo.cats.CatDTO;
import ru.itmo.owners.OwnerDTO;

import java.util.ArrayList;
import java.util.List;

public class CatMapper {
    public CatDTO fromCatToDTO(Cat cat){
        return new CatDTO(cat.getId(), cat.getName(), cat.getBirthday(), cat.getBreed(), cat.getColor(), OwnerMapper.fromOwnerToDTO(cat.getOwner()));
    }

    public Cat fromDTOToCat(CatDTO catDTO) {
        return new Cat(catDTO.getId(), catDTO.getName(), catDTO.getBirthday(), catDTO.getBreed(), catDTO.getColor(), new ArrayList<>(), OwnerMapper.fromDTOToOwner(catDTO.getOwner()));
    }
}
