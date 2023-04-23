package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.Dog;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Integer> {

    @NonNull
    List<Dog> findAll();
    @Query("SELECT d FROM Dog d WHERE d.owner.id = :ownerID")
    List<Dog> findAllByOwner_Id(@Param("ownerID") Integer ownerID);

    @Query("SELECT d FROM Dog d WHERE d.id = :id")
    Dog findDogById(@Param("id") Integer id);
}
