package ru.kramskoi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kramskoi.entity.Person;
import ru.kramskoi.repository.PersonRepository;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository repository;

    public PersonDetailsService(PersonRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        Person person = repository.findByUsername(username);
        return new PersonDetails(person);
    }
}
