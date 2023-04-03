package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.Vetclinic;

import java.util.List;

@Repository
public interface VetclinicRepository extends JpaRepository<Vetclinic, Integer> {

    @NonNull
    List<Vetclinic> findAll();
    Vetclinic findVetclinicById(Integer id);
}