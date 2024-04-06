package ru.kramskoi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private String birthday;
}