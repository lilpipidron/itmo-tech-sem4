package ru.kramskoi.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.sql.Date;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Jacksonized
@Builder
public class CatClientDTO {
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
