package ru.kramskoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kramskoi.entity.Person;
import ru.kramskoi.repository.PersonRepository;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    @Autowired
    private PersonRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        Person person = repository.findByUsername(username);
        return new PersonDetails(person);
    }
}
