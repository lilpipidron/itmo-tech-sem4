package ru.itmo.cats;

import ru.itmo.breeds.Breed;
import ru.itmo.colors.Color;
import ru.itmo.owners.Owner;
import ru.itmo.owners.OwnerRepositoryImpl;

import java.sql.Date;
import java.util.List;

public class CatServiceImpl implements CatService {
    private final CatRepositoryImpl catRepository;
    private final OwnerRepositoryImpl ownerRepository;

    public CatServiceImpl(CatRepositoryImpl catRepository, OwnerRepositoryImpl ownerRepository) {
        this.catRepository = catRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void addNewCat(int ownerId, String name, String birthday, String breed, String color) {
        if (ownerId < 0) {
            throw new IllegalArgumentException("Owner id can't be less than 0");
        }

        Owner owner = ownerRepository.getOwnerById(ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Incorrect owner id");
        }

        Date birthdayDate;
        try {
            birthdayDate = Date.valueOf(birthday);
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect format of birthday date");
        }

        Breed breedEnum;
        try {
            breedEnum = Breed.valueOf(breed);
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect breed");
        }

        Color colorEnum;
        try {
            colorEnum = Color.valueOf(color);
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect color");
        }
        Cat cat = new Cat(name, birthdayDate, breedEnum, colorEnum);
        catRepository.addNewCat(cat, ownerId);
    }
    private void catVerification(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Cat id can't be less than 0");
        }

        Cat cat = catRepository.getCatById(id);
        if (cat == null) {
            throw new IllegalArgumentException("Incorrect cat id");
        }
    }
    @Override
    public Cat getCatById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Cat id can't be less than 0");
        }

        Cat cat = catRepository.getCatById(id);
        if (cat == null) {
            throw new IllegalArgumentException("Incorrect cat id");
        }
        return cat;
    }

    @Override
    public List<Cat> getAllFriends(int id) {
        catVerification(id);
        return catRepository.getAllFriends(id);
    }

    @Override
    public void addFriend(int catId, int friendId) {
        catVerification(catId);
        catVerification(friendId);

        catRepository.addFriend(catId, friendId);
    }

    @Override
    public void deleteCat(int catId, int ownerId) {
        catVerification(catId);
        if (ownerId < 0) {
            throw new IllegalArgumentException("Owner id can't be less than 0");
        }

        Owner owner = ownerRepository.getOwnerById(ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Incorrect owner id");
        }
        catRepository.deleteCat(catId, ownerId);
    }
}
