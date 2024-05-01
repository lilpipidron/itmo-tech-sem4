package ru.kramskoi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kramskoi.entity.Person;
import ru.kramskoi.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository repository;

    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void addPerson(Person person) {
        repository.save(person);
    }
}
