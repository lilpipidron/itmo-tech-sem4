package ru.kramskoi.dto;

import jakarta.validation.constraints.*;
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
    @NotNull(message = "cat id can't be empty")
    @Min(value = 0, message="cat id can't be less 0")
    private Long id;

    @NotBlank(message = "name can't be empty")
    @Size(max = 20, message = "name can't be bigger than 20 symbols")
    private String name;

    @PastOrPresent
    private Date birthday;

    private Breed breed;

    private Color color;

    @NotNull(message = "owner id can't be empty")
    @Min(value = 0, message="owner id can't be less 0")
    private Long ownerId;
}
