package ru.kramskoi.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class OwnerDTO {
    @NotNull(message = "owner id can't be empty")
    @Min(value = 0, message = "owner id can't be less 0")
    private Long id;

    @NotBlank(message = "name can't be empty")
    @Size(max = 20, message = "name can't be bigger than 20 symbols")
    private String name;

    @PastOrPresent
    private Date birthday;

    @NotNull
    private Long personID;

}