package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.Owner;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    @NonNull
    List<Owner> findAll();

    Owner findByUsername(String username);
    Owner findOwnerById(Integer id);
}
