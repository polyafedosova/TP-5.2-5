package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.Dog;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Integer> {

    @NonNull
    List<Dog> findAll();
    List<Dog> findAllByOwner_Id(Integer ownerID);
    Dog findDogById(Integer id);
}
