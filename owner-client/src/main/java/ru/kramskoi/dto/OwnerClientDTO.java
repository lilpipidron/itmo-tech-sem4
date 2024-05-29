package ru.kramskoi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.sql.Date;

@Data
@AllArgsConstructor
@Jacksonized
@NoArgsConstructor
@Builder
public class OwnerClientDTO {
    private Long id;
    private String name;
    private Date birthday;
    private Long personID;
}
