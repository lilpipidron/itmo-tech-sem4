package ru.itmo.owners;

import lombok.Data;
import ru.itmo.cats.Cat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

@Data
public class Owner {
    private final UUID id;
    private final String name;
    private final Date birthday;
    private final ArrayList<Cat> ats;
}
