package ru.kramskoi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.entity.Cat;
import ru.kramskoi.entity.Person;
import ru.kramskoi.exception.CatNotFound;
import ru.kramskoi.mapper.CatMapper;
import ru.kramskoi.repository.CatRepository;
import ru.kramskoi.repository.PersonRepository;

import java.security.Principal;
import java.util.List;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;
    private final PersonRepository personRepository;

    public CatServiceImpl(CatRepository catRepository, PersonRepository personRepository) {
        this.catRepository = catRepository;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public void addCat(Cat cat, Principal principal) {
        cat.setOwner(personRepository.findByUsername(principal.getName()).getOwner());
        catRepository.save(cat);
    }

    @Override
    @Transactional(readOnly = true)
    public CatDTO findCatByID(Long id, Principal principal) {
        Cat cat;
        Person person = personRepository.findByUsername(principal.getName());
        if (person.getRoles() == "ADMIN") {
            cat = catRepository.getCatById(id);
        } else {
            cat = catRepository.getCatByIdAndOwnerId(id, person.getOwner().getId());
        }
        if (cat == null) {
            throw new CatNotFound();
        }
        return CatMapper.fromCatToDTO(cat);
    }

    private List<CatDTO> findCatsByColor(Color color, Long ownerId) {
        List<Cat> catList;
        if (ownerId != -1) {
            catList = catRepository.getCatsByColorAndOwnerId(color, ownerId);
        } else {
            catList = catRepository.getCatsByColor(color);
        }
        List<CatDTO> catDTOList = catList.stream()
                .map(CatMapper::fromCatToDTO)
                .toList();
        if (catDTOList.isEmpty()) {
            throw new CatNotFound();
        }
        return catDTOList;
    }

    private List<CatDTO> findCatsByBreed(Breed breed, Long ownerId) {
        List<Cat> catList;
        if (ownerId != -1) {
            catList = catRepository.getCatsByBreedAndOwnerId(breed, ownerId);
        } else {
            catList = catRepository.getCatsByBreed(breed);
        }
        List<CatDTO> catDTOList = catList.stream()
                .map(CatMapper::fromCatToDTO)
                .toList();
        if (catDTOList.isEmpty()) {
            throw new CatNotFound();
        }
        return catDTOList;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> getCatsByColorOrBreed(Color color, Breed breed, Principal principal) {
        Person person = personRepository.findByUsername(principal.getName());
        Long ownerId = -1L;
        if (person.getRoles() == "USER") {
            ownerId = person.getOwner().getId();
        }
        if (breed == null) {
            return findCatsByColor(color, ownerId);
        } else if (color == null) {
            return findCatsByBreed(breed, ownerId);
        }

        List<CatDTO> catDTOList = catRepository.getCatsByColorOrBreedAndOwnerId(color, breed, ownerId)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();

        if (catDTOList.isEmpty()) {
            throw new CatNotFound();
        }

        return catDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> getFriendsById(Long id, Principal principal) {
        Person person = personRepository.findByUsername(principal.getName());
        Cat cat;
        if (person.getRoles() == "ADMIN") {
            cat = catRepository.getCatById(id);
        } else {
            cat = catRepository.getCatByIdAndOwnerId(id, person.getOwner().getId());
        }
        List<CatDTO> catDTOList = cat
                .getFriends()
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();
        if (catDTOList.isEmpty()) {
            throw new CatNotFound();
        }

        return catDTOList;
    }

    @Override
    @Transactional
    public void addFriend(Long catId, Long friendId, Principal principal) {
        Cat cat;
        Person person = personRepository.findByUsername(principal.getName());
        if (person.getRoles() == "ADMIN") {
            cat = catRepository.getCatById(catId);
        } else {
            cat = catRepository.getCatByIdAndOwnerId(catId, person.getOwner().getId());
        }
        Cat friend = catRepository.getCatById(friendId);
        if (cat == null || friend == null) {
            throw new CatNotFound();
        }
        cat.addFriend(friend);
        friend.addFriend(cat);
        catRepository.save(cat);
        catRepository.save(friend);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> getAllCatsByOwnerId(Long id, Principal principal) {
        Long ownerId = personRepository.findByUsername(principal.getName()).getOwner().getId();
        List<CatDTO> catDTOList = catRepository.getCatsByOwnerId(ownerId)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();

        if (catDTOList.isEmpty()) {
            throw new CatNotFound();
        }

        return catDTOList;
    }

    @Override
    @Transactional
    public void updateCat(Cat cat, Principal principal) {
        Person person = personRepository.findByUsername(principal.getName());
        Cat prevCat;
        if (person.getRoles() == "ADMIN") {
            prevCat = catRepository.getCatById(cat.getId());
        } else {
            prevCat = catRepository.getCatByIdAndOwnerId(cat.getId(), person.getOwner().getId());
        }
        if (prevCat == null) {
            throw new CatNotFound();
        }
        prevCat.setName(cat.getName());
        prevCat.setBirthday(cat.getBirthday());
        prevCat.setBreed(cat.getBreed());
        prevCat.setColor(cat.getColor());
        catRepository.save(cat);
    }

    @Override
    @Transactional
    public void deleteCat(Long id, Principal principal) {
        Person person = personRepository.findByUsername(principal.getName());
        if (catRepository.getCatById(id) == null) {
            throw new CatNotFound();
        }
        if (person.getRoles() == "ADMIN") {
            catRepository.deleteById(id);
            return;
        } else {
            Cat cat = catRepository.getCatByIdAndOwnerId(id, person.getOwner().getId());
            if (cat == null) {
                throw new CatNotFound();
            }
            catRepository.deleteById(id);
        }
    }
}
