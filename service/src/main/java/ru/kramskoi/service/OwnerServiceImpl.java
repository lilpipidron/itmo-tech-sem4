package ru.kramskoi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Owner;
import ru.kramskoi.mapper.OwnerMapper;
import ru.kramskoi.repository.CatRepository;
import ru.kramskoi.repository.OwnerRepository;


@Service
public class OwnerServiceImpl implements OwnerService {
    final
    OwnerRepository ownerRepository;
    final
    CatRepository catRepository;

    public OwnerServiceImpl(CatRepository catRepository, OwnerRepository ownerRepository) {
        this.catRepository = catRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    @Transactional
    public void addOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    @Override
    @Transactional(readOnly = true)
    public OwnerDTO getOwnerByID(Long id) {
        OwnerDTO ownerDTO = OwnerMapper.fromOwnerToDTO(ownerRepository.getOwnerById(id));
        if (ownerDTO == null) {
            throw new IllegalArgumentException();
        }
        return ownerDTO;
    }

    @Override
    @Transactional
    public void updateOwner(Owner owner) {
        Owner prevOwner = ownerRepository.getOwnerById(owner.getId());
        prevOwner.setBirthday(owner.getBirthday());
        prevOwner.setName(owner.getName());
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}
