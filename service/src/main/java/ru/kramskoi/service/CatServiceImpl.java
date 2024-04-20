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
import java.util.stream.Collectors;

@Service
public class CatServiceImpl implements CatService {
    final
    CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    @Transactional
    public void addCat(Cat cat) {
        catRepository.save(cat);
    }

    @Override
    @Transactional
    public CatDTO findCatByID(Long id) {
        return CatMapper.fromCatToDTO(catRepository.getCatById(id));
    }

    @Override
    @Transactional
    public List<CatDTO> findCatsByColor(Color color) {
        return catRepository.getCatsByColor(color)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CatDTO> findCatsByBreed(Breed breed) {
        return catRepository.getCatsByBreed(breed)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CatDTO> getFriendsById(Long id) {
        return catRepository.getCatById(id)
                .getFriends()
                .stream()
                .map(CatMapper::fromCatToDTO)
                .collect(Collectors.toList());
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
    @Transactional
    public List<CatDTO> getAllCatsByOwnerId(Long id) {
        return catRepository.getCatsByOwnerId(id)
                .stream()
                .map(CatMapper::fromCatToDTO)
                .collect(Collectors.toList());
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
