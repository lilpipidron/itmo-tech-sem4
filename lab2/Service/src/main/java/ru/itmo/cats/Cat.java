package ru.itmo.cats;

import lombok.Data;
import ru.itmo.breeds.Breed;
import ru.itmo.owners.Owner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Data
public class Cat {
    private final UUID id;
    private final String name;
    private final Date birthday;
    private final Breed breed;
    private final Owner owner;
    private final ArrayList<Cat> friends;
}
