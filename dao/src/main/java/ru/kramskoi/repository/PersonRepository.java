package ru.kramskoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kramskoi.entity.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
}
