package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.Treatment;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {

    @NonNull
    List<Treatment> findAll();
    @Query("SELECT t FROM Treatment t WHERE t.vetclinic.id = :vetclinicID")
    List<Treatment> findAllByVetclinic_Id(@Param("vetclinicID") Integer vetclinicID);
    @Query("SELECT t FROM Treatment t WHERE t.id = :id")
    Treatment findTreatmentById(@Param("id") Integer id);
}
