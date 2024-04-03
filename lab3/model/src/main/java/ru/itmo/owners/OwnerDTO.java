package ru.itmo.owners;

import lombok.Data;

import java.sql.Date;

@Data
public class OwnerDTO {
    private final long id;
    private final String name;
    private final Date birthday;
}