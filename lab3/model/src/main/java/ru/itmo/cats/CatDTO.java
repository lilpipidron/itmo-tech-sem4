package ru.itmo.cats;

import lombok.Data;
import ru.itmo.breeds.Breed;
import ru.itmo.colors.Color;
import ru.itmo.owners.OwnerDTO;

import java.sql.Date;

@Data
public class CatDTO {
    private final long id;
    private final String name;
    private final Date birthday;
    private final Breed breed;
    private final Color color;
    private final OwnerDTO owner;
}