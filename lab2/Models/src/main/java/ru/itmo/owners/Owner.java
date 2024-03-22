package ru.itmo.owners;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private final String name;
    @Column(name = "birthday", nullable = false)
    private final Date birthday;

    public Owner() {
        this.name = null;
        this.birthday = null;
    }

    public Owner(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }
}
