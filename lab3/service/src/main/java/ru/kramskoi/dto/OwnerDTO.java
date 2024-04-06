package ru.kramskoi.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class OwnerDTO {
  private final Long id;
  private final String name;
  private final Date birthday;
}