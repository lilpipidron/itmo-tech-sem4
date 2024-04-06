package ru.kramskoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kramskoi.breeds.Breed;
import ru.kramskoi.colors.Color;
import ru.kramskoi.entity.Cat;

import java.util.List;


@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
  Cat getCatById(Long id);
  List<Cat> getCatsByBreed(Breed breed);
  List<Cat> getCatsByColor(Color color);
  List<Cat> getCatsByOwnerId(Long ownerId);
  @Query(value = "SELECT c.* FROM cats c WHERE c.id IN (SELECT friend_id FROM cat_friend WHERE cat_id = :id)", nativeQuery = true)
  List<Cat> getFriendsById(@Param("id") Long id);
}
