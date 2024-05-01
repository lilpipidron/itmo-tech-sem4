package ru.kramskoi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.dto.CatDTO;
import ru.kramskoi.entity.Cat;
import ru.kramskoi.mapper.CatMapper;
import ru.kramskoi.repository.CatRepository;

import java.util.List;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    @Transactional
    public void addCat(Cat cat) {
        catRepository.save(cat);
    }

    @Override
    @Transactional(readOnly = true)
    public CatDTO findCatByID(Long id) {
        Cat cat = catRepository.getCatById(id);
        if (cat == null) {
            throw new IllegalArgumentException();
        }
        return CatMapper.fromCatToDTO(cat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> findCatsByColor(Color color) {
        List<CatDTO> catDTOList = catRepository.getCatsByColor(color)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();
        if (catDTOList.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return catDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> findCatsByBreed(Breed breed) {
        List<CatDTO> catDTOList = catRepository.getCatsByBreed(breed)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();
        if (catDTOList.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return catDTOList;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> getCatsByColorOrBreed(Color color, Breed breed) {
        if (breed == null) {
            return findCatsByColor(color);
        } else if (color == null) {
            return findCatsByBreed(breed);
        }

        List<CatDTO> catDTOList = catRepository.getCatsByColorOrBreed(color, breed)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();
        if (catDTOList.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return catDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> getFriendsById(Long id) {
        List<CatDTO> catDTOList = catRepository.getCatById(id)
                .getFriends()
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();
        if (catDTOList.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return catDTOList;
    }

    @Override
    @Transactional
    public void addFriend(Long catId, Long friendId) {
        Cat cat = catRepository.getCatById(catId);
        Cat friend = catRepository.getCatById(friendId);
        cat.addFriend(friend);
        friend.addFriend(cat);
        catRepository.save(cat);
        catRepository.save(friend);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> getAllCatsByOwnerId(Long id) {
        List<CatDTO> catDTOList = catRepository.getCatsByOwnerId(id)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .toList();
        if (catDTOList.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return catDTOList;
    }

    @Override
    @Transactional
    public void updateCat(Cat cat) {
        Cat prevCat = catRepository.getCatById(cat.getId());
        prevCat.setName(cat.getName());
        prevCat.setBirthday(cat.getBirthday());
        prevCat.setBreed(cat.getBreed());
        prevCat.setColor(cat.getColor());
        catRepository.save(cat);
    }

    @Override
    @Transactional
    public void deleteCat(Long id) {
        catRepository.deleteById(id);
    }
}
