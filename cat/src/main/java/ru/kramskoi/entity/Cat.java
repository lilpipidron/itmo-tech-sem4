package ru.kramskoi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import ru.kramskoi.dto.Breed;
import ru.kramskoi.dto.Color;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cats")
@Entity
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "breed", nullable = false)
    private Breed breed;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "color", nullable = false)
    private Color color;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "cat_friend",
            joinColumns = @JoinColumn(name = "cat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private List<Cat> friends = new ArrayList<>();

    private Long ownerID;

    public Cat(String name, Date birthdayDate, Breed breedEnum, Color colorEnum, Long ownerID) {
        this.name = name;
        this.birthday = birthdayDate;
        this.breed = breedEnum;
        this.color = colorEnum;
        this.ownerID = ownerID;
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


