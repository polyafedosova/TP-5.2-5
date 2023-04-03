package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.Treatment;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {

    @NonNull
    List<Treatment> findAll();
    List<Treatment> findAllByVetclinic_Id(Integer vetclinicID);
    Treatment findTreatmentById(Integer id);
}
