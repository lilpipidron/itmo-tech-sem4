package ru.kramskoi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class OwnerDTO {
  private Long id;
  private String name;
  private String birthday;
}