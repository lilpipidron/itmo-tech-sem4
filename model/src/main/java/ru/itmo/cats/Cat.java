package ru.itmo.cats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import ru.itmo.breeds.Breed;
import ru.itmo.colors.Color;
import ru.itmo.owners.Owner;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "cat_friend",
            joinColumns = @JoinColumn(name = "cat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private List<Cat> friends = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Owner owner;

    public Cat() {
        this.name = null;
        this.birthday = null;
        this.breed = null;
        this.color = null;
        this.owner = null;
        this.friends = null;
    }

    public Cat(String name, Date birthdayDate, Breed breedEnum, Color colorEnum, Owner owner) {
        this.name = name;
        this.birthday = birthdayDate;
        this.breed = breedEnum;
        this.color = colorEnum;
        this.owner = owner;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Cat cat = (Cat) o;
        return Objects.equals(getId(), cat.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public void addFriend(Cat cat) {
        if (!friends.contains(cat)) {
            friends.add(cat);
        }
    }
}

