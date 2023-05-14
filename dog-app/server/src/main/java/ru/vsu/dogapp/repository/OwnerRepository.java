package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.Owner;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    @NonNull
    List<Owner> findAll();
    @Query("SELECT o FROM Owner o WHERE o.username = :username")
    Owner findByUsername(@Param("username") String username);
    @Query("SELECT o FROM Owner o WHERE o.id = :id")
    Owner findOwnerById(@Param("id") Integer id);
}
