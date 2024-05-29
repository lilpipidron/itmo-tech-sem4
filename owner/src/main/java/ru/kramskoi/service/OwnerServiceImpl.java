package ru.kramskoi.service;

import org.springframework.stereotype.Service;
import ru.kramskoi.dto.OwnerClientDTO;
import ru.kramskoi.dto.OwnerMessage;
import ru.kramskoi.entity.Owner;
import ru.kramskoi.exception.OwnerNotFound;
import ru.kramskoi.repository.OwnerRepository;


@Service
public class OwnerServiceImpl implements OwnerService {
    final
    OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Long addOwner(OwnerMessage owner) {
        return ownerRepository.save(
                new Owner(
                        owner.getName(),
                        owner.getBirthday(),
                        owner.getPersonID()
                )
        ).getId();
    }

    @Override
    public OwnerClientDTO getOwnerByID(Long id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(OwnerNotFound::new);
        return new OwnerClientDTO(
                owner.getId(),
                owner.getName(),
                owner.getBirthday(),
                owner.getPersonID()
        );
    }

    @Override
    public void updateOwner(OwnerMessage owner) {
        Owner own = ownerRepository.findById(owner.getId()).orElseThrow(OwnerNotFound::new);

        own.setName(owner.getName());
        owner.setBirthday(owner.getBirthday());
        ownerRepository.save(own);
    }

    @Override
    public void deleteOwner(Long id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(OwnerNotFound::new);
        ownerRepository.delete(owner);
    }
}
