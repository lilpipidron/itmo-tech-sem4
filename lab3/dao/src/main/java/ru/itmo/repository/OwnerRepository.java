package ru.itmo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.owners.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner getOwnerById(Long id);

}