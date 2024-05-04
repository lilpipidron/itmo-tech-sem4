package ru.kramskoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kramskoi.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUsername(String username);
}
