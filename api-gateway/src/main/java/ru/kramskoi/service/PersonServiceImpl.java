package ru.kramskoi.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kramskoi.entity.Person;
import ru.kramskoi.models.OwnerDTO;
import ru.kramskoi.repository.PersonRepository;

import java.security.Principal;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PersonServiceImpl(PersonRepository repository) {
        this.personRepository = repository;
        passwordEncoder = new BCryptPasswordEncoder();
    }


    @Transactional
    public void addPerson(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }

    public Person getPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void addOwner(Principal principal, OwnerDTO ownerDTO) {
        Person person = getPersonByUsername(principal.getName());
        person.setOwnerID(ownerDTO.getId());
        personRepository.save(person);
    }
}
