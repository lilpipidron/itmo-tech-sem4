package ru.kramskoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.entity.Cat;

import java.util.List;


@Repository

public interface CatRepository extends JpaRepository<Cat, Long> {
    Cat getCatByIdAndOwnerId(Long id, Long ownerId);

    Cat getCatById(Long id);

    List<Cat> getCatsByBreedAndOwnerId(Breed breed, Long ownerId);

    List<Cat> getCatsByColorAndOwnerId(Color color, Long ownerId);

    List<Cat> getCatsByOwnerId(Long ownerId);

    List<Cat> getCatsByColorOrBreedAndOwnerId(Color color, Breed breed, Long ownerId);
}
