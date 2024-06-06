package ru.kramskoi.mapper;

import lombok.experimental.UtilityClass;
import ru.kramskoi.entity.Cat;
import ru.kramskoi.models.CatDTO;

import java.util.ArrayList;

@UtilityClass
public class CatMapper {

  public CatDTO fromCatToDTOClient(Cat cat) {
    if (cat == null){
      return null;
    }
    return new CatDTO(cat.getId(), cat.getName(), cat.getBirthday(), cat.getBreed(), cat.getColor(), cat.getOwnerID());
  }

  public Cat fromDTOToCat(CatDTO catDTO) {
    if (catDTO == null){
      return null;
    }
    return new Cat(catDTO.getId(), catDTO.getName(), catDTO.getBirthday(), catDTO.getBreed(), catDTO.getColor(), new ArrayList<>(), catDTO.getOwnerId());
  }
}
