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
        System.out.println(cat);
        System.out.println(principal.getName());
        System.out.println(personRepository.findByUsername(principal.getName()).getOwner());
        cat.setOwner(personRepository.findByUsername(principal.getName()).getOwner());
        catRepository.save(cat);
    }

    @Override
    @Transactional(readOnly = true)
    public CatDTO findCatByID(Long id, Principal principal) {
        Cat cat = catRepository.getCatById(id);
        if (cat == null) {
            throw new CatNotFound();
        }

        String username = principal.getName();
        Person person = personRepository.findByUsername(username);
        List<Cat> ownersCats = person.getOwner().getCats();
        if (ownersCats.stream().findFirst().filter(c -> c.getId() == cat.getId()).isEmpty()) {
            throw new CatNotFound();
        }

        return CatMapper.fromCatToDTO(cat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> findCatsByColor(Color color, Principal principal) {
        List<CatDTO> catDTOList = new java.util.ArrayList<>(catRepository.getCatsByColor(color)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList());
        if (catDTOList.isEmpty()) {
            throw new CatNotFound();
        }
        return catDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> findCatsByBreed(Breed breed, Principal principal) {
        List<CatDTO> catDTOList = catRepository.getCatsByBreed(breed)
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
    public List<CatDTO> getCatsByColorOrBreed(Color color, Breed breed, Principal principal) {
        if (breed == null) {
            return findCatsByColor(color, principal);
        } else if (color == null) {
            return findCatsByBreed(breed, principal);
        }

        List<CatDTO> catDTOList = catRepository.getCatsByColorOrBreed(color, breed)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();

        List<Long> ownersCats = personRepository.findByUsername(principal.getName())
                .getOwner()
                .getCats()
                .stream()
                .map(Cat::getId)
                .toList();

        catDTOList.removeIf(catDTO -> !ownersCats.contains(catDTO.getId()));
        if (catDTOList.isEmpty()) {
            throw new CatNotFound();
        }
        return catDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> getFriendsById(Long id, Principal principal) {
        List<CatDTO> catDTOList = catRepository.getCatById(id)
                .getFriends()
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();
        if (catDTOList.isEmpty()) {
            throw new CatNotFound();
        }

        List<Long> ownersCats = personRepository.findByUsername(principal.getName())
                .getOwner()
                .getCats()
                .stream()
                .map(Cat::getId)
                .toList();

        catDTOList.removeIf(catDTO -> !ownersCats.contains(catDTO.getId()));
        if (catDTOList.isEmpty()) {
            throw new CatNotFound();
        }

        return catDTOList;
    }

    @Override
    @Transactional
    public void addFriend(Long catId, Long friendId, Principal principal) {
        Cat cat = catRepository.getCatById(catId);
        Cat friend = catRepository.getCatById(friendId);
        List<Long> ownersCats = personRepository.findByUsername(principal.getName())
                .getOwner()
                .getCats()
                .stream()
                .map(Cat::getId)
                .toList();
        if (!ownersCats.contains(cat.getId())) {
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
        List<CatDTO> catDTOList = catRepository.getCatsByOwnerId(id)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();

        List<Long> ownersCats = personRepository.findByUsername(principal.getName())
                .getOwner()
                .getCats()
                .stream()
                .map(Cat::getId)
                .toList();

        catDTOList.removeIf(catDTO -> !ownersCats.contains(catDTO.getId()));
        if (catDTOList.isEmpty()) {
            throw new CatNotFound();
        }

        return catDTOList;
    }

    @Override
    @Transactional
    public void updateCat(Cat cat, Principal principal) {
        Cat prevCat = catRepository.getCatById(cat.getId());
        List<Long> ownersCats = personRepository.findByUsername(principal.getName())
                .getOwner()
                .getCats()
                .stream()
                .map(Cat::getId)
                .toList();
        if (!ownersCats.contains(cat.getId())) {
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
        List<Long> ownersCats = personRepository.findByUsername(principal.getName())
                .getOwner()
                .getCats()
                .stream()
                .map(Cat::getId)
                .toList();
        if (!ownersCats.contains(id)) {
            throw new CatNotFound();
        }
        catRepository.deleteById(id);
    }
}
