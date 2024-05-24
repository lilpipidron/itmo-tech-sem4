package ru.kramskoi.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.sql.Date;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@Jacksonized
public class Cat {
    private Long id;

    private String name;

    private Date birthday;

    private Breed breed;

    private Color color;

    private Long ownerId;
}
