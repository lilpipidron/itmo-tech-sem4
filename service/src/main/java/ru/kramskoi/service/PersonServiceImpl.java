package ru.kramskoi.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Owner;
import ru.kramskoi.entity.Person;
import ru.kramskoi.repository.OwnerRepository;
import ru.kramskoi.repository.PersonRepository;

import java.security.Principal;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;
    private OwnerRepository ownerRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public PersonServiceImpl(PersonRepository repository, OwnerRepository ownerRepository) {
        this.personRepository = repository;
        this.ownerRepository = ownerRepository;
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
        Owner owner = ownerRepository.getOwnerById(ownerDTO.getId());
        owner.setPerson(person);
        person.setOwner(owner);
        ownerRepository.save(owner);
        personRepository.save(person);
    }
}
