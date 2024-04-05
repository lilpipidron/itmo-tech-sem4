package ru.itmo.cats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.itmo.breeds.Breed;
import ru.itmo.colors.Color;
import ru.itmo.mappers.CatMapper;
import ru.itmo.repository.CatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(basePackages = {"ru.itmo.repository"})
public class CatServiceImpl implements CatService {
    @Autowired
    CatRepository catRepository;

    @Override
    public void addCat(Cat cat) {
        catRepository.save(cat);
    }

    @Override
    public CatDTO findCatByID(long id) {
        return CatMapper.fromCatToDTO(catRepository.getCatById(id));
    }

    @Override
    public List<CatDTO> findCatsByColor(Color color) {
        List<CatDTO> catDTOS = new ArrayList<>();
        List<Cat> cats = catRepository.getCatsByColor(color);
        for (Cat cat : cats) {
            catDTOS.add(CatMapper.fromCatToDTO(cat));
        }
        return catDTOS;
    }

    @Override
    public List<CatDTO> findCatsByBreed(Breed breed) {
        List<CatDTO> catDTOS = new ArrayList<>();
        List<Cat> cats = catRepository.getCatsByBreed(breed);
        for (Cat cat : cats) {
            catDTOS.add(CatMapper.fromCatToDTO(cat));
        }
        return catDTOS;
    }

    @Override
    public List<CatDTO> getAllFriends(long id) {
        List<CatDTO> catDTOS = new ArrayList<>();
        List<Cat> cats = catRepository.getFriendsById(id);
        for (Cat cat : cats) {
            catDTOS.add(CatMapper.fromCatToDTO(cat));
        }
        return catDTOS;
    }

    @Override
    public void addFriend(long catId, long friendId) {
        Cat cat = catRepository.getCatById(catId);
        Cat friend = catRepository.getCatById(friendId);
        cat.addFriend(friend);
        friend.addFriend(cat);
        catRepository.save(cat);
        catRepository.save(friend);
    }

    @Override
    public List<CatDTO> getAllCatsByOwnerId(long id) {
        List<CatDTO> catDTOS = new ArrayList<>();
        List<Cat> cats = catRepository.getCatsByOwnerId(id);
        for (Cat cat : cats) {
            catDTOS.add(CatMapper.fromCatToDTO(cat));
        }
        return catDTOS;
    }
}
