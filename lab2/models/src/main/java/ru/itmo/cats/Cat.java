package ru.itmo.cats;

import lombok.Data;
import ru.itmo.breeds.Breed;
import ru.itmo.colors.Color;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private final String name;

    @Column(name = "birthday", nullable = false)
    private final Date birthday;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "breed", nullable = false)
    private final Breed breed;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "color", nullable = false)
    private final Color color;

    public Cat() {
        this.name = null;
        this.birthday = null;
        this.breed = null;
        this.color = null;
    }

    public Cat(String name, Date birthday, Breed breed, Color color) {
        this.name = name;
        this.birthday = birthday;
        this.breed = breed;
        this.color = color;
    }
}
