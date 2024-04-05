package ru.itmo.owners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.itmo.cats.Cat;
import ru.itmo.cats.CatDTO;
import ru.itmo.cats.CatRepository;
import ru.itmo.mappers.CatMapper;
import ru.itmo.mappers.OwnerMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(basePackages = {"ru.itmo.repository"})
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    CatRepository catRepository;

    @Override
    public void addOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @Override
    public OwnerDTO getOwnerByID(long id) {
        return OwnerMapper.fromOwnerToDTO(ownerRepository.getOwnerById(id));
    }
}
