package ru.kramskoi.dto;


import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.sql.Date;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@Jacksonized
public class OwnerMessage {
    private Long id;

    private String name;

    private Date birthday;

    private Long personID;
}