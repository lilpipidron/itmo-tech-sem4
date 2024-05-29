package ru.kramskoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kramskoi.entity.Owner;

@Repository

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
