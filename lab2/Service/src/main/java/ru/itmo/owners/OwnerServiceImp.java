package ru.itmo.owners;

import ru.itmo.cats.Cat;
import ru.itmo.cats.CatRepositoryImp;

import java.sql.Date;
import java.util.ArrayList;

public class OwnerServiceImp implements OwnerService{
    private final CatRepositoryImp catRepository;
    private final OwnerRepositoryImp ownerRepository;

    public OwnerServiceImp(CatRepositoryImp catRepository, OwnerRepositoryImp ownerRepository) {
        this.catRepository = catRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void addNewOwner(String name, String birthday) {
        Date birthdayDate;
        try {
            birthdayDate = Date.valueOf(birthday);
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect birthday date");
        }
        Owner owner = new Owner(name, birthdayDate);
        ownerRepository.addNewOwner(owner);
    }

    @Override
    public Owner getOwnerById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Owner id can't be less than 0");
        }

        Owner owner = ownerRepository.getOwnerById(id);
        if (owner == null) {
            throw new IllegalArgumentException("Incorrect owner id");
        }
        return owner;
    }

    @Override
    public void addCat(int ownerId, int catId) {
        getOwnerById(ownerId);

        if (catId < 0) {
            throw new IllegalArgumentException("Cat id can't be less 0");
        }

        Cat cat = catRepository.getCatById(catId);
        if (cat == null) {
            throw new IllegalArgumentException("Incorrect cat id");
        }

        ownerRepository.addCat(ownerId, catId);
    }

    @Override
    public void deleteOwner(int ownerId) {
        getOwnerById(ownerId);
        ownerRepository.deleteOwner(ownerId);

    }

    @Override
    public ArrayList<Integer> getAllCatsId(int ownerId) {
        getOwnerById(ownerId);
        return ownerRepository.getAllCatsId(ownerId);
    }
}
