package ru.kramskoi.mapper;

import lombok.experimental.UtilityClass;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.entity.Cat;

import java.util.ArrayList;

@UtilityClass
public class CatMapper {

  public CatDTO fromCatToDTO(Cat cat) {
    return new CatDTO(cat.getId(), cat.getName(), cat.getBirthday(), cat.getBreed(), cat.getColor(), OwnerMapper.fromOwnerToDTO(cat.getOwner()));
  }

  public Cat fromDTOToCat(CatDTO catDTO) {
    return new Cat(catDTO.getId(), catDTO.getName(), catDTO.getBirthday(), catDTO.getBreed(), catDTO.getColor(), new ArrayList<>(), OwnerMapper.fromDTOToOwner(catDTO.getOwner()));
  }
}
