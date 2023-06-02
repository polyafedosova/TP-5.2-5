package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.Vetclinic;

import java.util.List;

@Repository
public interface VetclinicRepository extends JpaRepository<Vetclinic, Integer> {

    @NonNull
    List<Vetclinic> findAll();
    @Query("SELECT v FROM Vetclinic v WHERE v.id = :id")
    Vetclinic findVetclinicById(@Param("id") Integer id);

    @Query("SELECT v FROM Vetclinic v WHERE LOWER(v.city) LIKE LOWER(:city)")
    List<Vetclinic> findAllByCity(@Param("city") String city);
}