package ru.kramskoi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kramskoi.dto.PersonDTO;
import ru.kramskoi.mapper.PersonMapper;
import ru.kramskoi.service.PersonServiceImpl;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonServiceImpl personService;

    public PersonController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @PostMapping
    public void addPerson(@RequestBody PersonDTO personDTO) {
        personService.addPerson(PersonMapper.fromDTOToPerson(personDTO));
    }
}
