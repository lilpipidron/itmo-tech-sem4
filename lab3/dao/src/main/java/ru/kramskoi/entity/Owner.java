package ru.kramskoi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owners")
@Entity
public class Owner {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "birthday", nullable = false)
  private Date birthday;

  @OneToMany(mappedBy="id", orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Cat> cats = new ArrayList<>();

  public Owner(Long id, String name, Date birthday) {
    this.id = id;
    this.name = name;
    this.birthday = birthday;
  }

  public Owner(Long id) {
    this.id = id;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Owner owner = (Owner) o;
    return  Objects.equals(getId(), owner.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }

  public void addCat(Cat cat) {
    if (!cats.contains(cat)) {
      cats.add(cat);
    }
  }

}