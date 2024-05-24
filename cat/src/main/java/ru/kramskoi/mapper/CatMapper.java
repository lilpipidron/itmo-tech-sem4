package ru.kramskoi.mapper;

import lombok.experimental.UtilityClass;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.entity.Cat;
import ru.kramskoi.entity.Owner;

import java.util.ArrayList;

@UtilityClass
public class CatMapper {

  public CatDTO fromCatToDTO(Cat cat) {
    if (cat == null){
      return null;
    }
    return new CatDTO(cat.getId(), cat.getName(), cat.getBirthday(), cat.getBreed(), cat.getColor(), cat.getOwner().getId());
  }

  public Cat fromDTOToCat(CatDTO catDTO) {
    if (catDTO == null){
      return null;
    }
    return new Cat(catDTO.getId(), catDTO.getName(), catDTO.getBirthday(), catDTO.getBreed(), catDTO.getColor(), new ArrayList<>(),new Owner(catDTO.getOwnerId()));
  }
}
