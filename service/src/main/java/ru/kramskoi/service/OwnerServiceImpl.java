package ru.kramskoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.entity.Owner;
import ru.kramskoi.mapper.OwnerMapper;
import ru.kramskoi.repository.CatRepository;
import ru.kramskoi.repository.OwnerRepository;


@Service
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
  public OwnerDTO getOwnerByID(Long id) {
    return OwnerMapper.fromOwnerToDTO(ownerRepository.getOwnerById(id));
  }
}
