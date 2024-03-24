package ru.itmo.owners;

import ru.itmo.cats.Cat;
import ru.itmo.cats.CatRepositoryImpl;

import java.sql.Date;
import java.util.List;

public class OwnerServiceImpl implements OwnerService{
    private final CatRepositoryImpl catRepository;
    private final OwnerRepositoryImpl ownerRepository;

    public OwnerServiceImpl(CatRepositoryImpl catRepository, OwnerRepositoryImpl ownerRepository) {
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
        System.out.println(owner.getId());
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

    private void ownerVerification(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Owner id can't be less than 0");
        }

        Owner owner = ownerRepository.getOwnerById(id);
        if (owner == null) {
            throw new IllegalArgumentException("Incorrect owner id");
        }
    }

    @Override
    public void addCat(int ownerId, int catId) {
        ownerVerification(ownerId);

        if (catId < 0) {
            throw new IllegalArgumentException("Cat id can't be less 0");
        }

        Cat cat = catRepository.getCatById(catId);
        if (cat == null) {
            throw new IllegalArgumentException("Incorrect cat id");
        }
    }

    @Override
    public void deleteOwner(int ownerId) {
        ownerVerification(ownerId);
        ownerRepository.deleteOwner(ownerId);

    }

    @Override
    public List<Integer> getAllCatsId(int ownerId) {
        ownerVerification(ownerId);
        return ownerRepository.getAllCatsId(ownerId);
    }
}
