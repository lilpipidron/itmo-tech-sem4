package ru.itmo.owners;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Data
@Table(name = "owners")
public class Owner {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private final int id;
    @Column(name = "name", nullable = false)
    private final String name;
    @Column(name = "birthday", nullable = false)
    private final Date birthday;

    public Owner() {
        this.id = -1;
        this.name = null;
        this.birthday = null;
    }
}
