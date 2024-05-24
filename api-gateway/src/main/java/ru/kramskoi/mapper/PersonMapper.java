package ru.kramskoi.mapper;

import lombok.experimental.UtilityClass;
import ru.kramskoi.dto.PersonDTO;
import ru.kramskoi.entity.Person;

@UtilityClass
public class PersonMapper {
    public PersonDTO fromPersonToDTO(Person person) {
        return new PersonDTO(person.getId(), person.getUsername(), person.getPassword(), person.getRoles());
    }

    public Person fromDTOToPerson(PersonDTO personDTO) {
        return new Person(personDTO.getId(), personDTO.getUsername(), personDTO.getPassword(), personDTO.getRoles());
    }
}
