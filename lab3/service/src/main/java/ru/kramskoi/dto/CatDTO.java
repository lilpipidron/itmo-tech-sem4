package ru.kramskoi.dto;

import lombok.Data;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;

import java.sql.Date;

@Data
public class CatDTO {
  private final Long id;
  private final String name;
  private final Date birthday;
  private final Breed breed;
  private final Color color;
  private final OwnerDTO owner;
}
