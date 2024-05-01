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
    private final PersonRepository repository;

    public PersonDetailsService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = repository.findByUsername(username);
        return person.map(PersonDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
