package ru.kramskoi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;

import java.sql.Date;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CatDTO {
  private Long id;
  private String name;
  private Date birthday;
  private Breed breed;
  private Color color;
  private Long ownerId;
}
