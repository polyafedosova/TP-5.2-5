package ru.vsu.dogapp.service;

import org.springframework.stereotype.Service;
import ru.vsu.dogapp.entity.Treatment;
import ru.vsu.dogapp.repository.TreatmentRepository;

import java.util.List;

@Service
public class TreatmentService {

    private final TreatmentRepository repository;

    public TreatmentService(TreatmentRepository repository) {
        this.repository = repository;
    }

    public void save(Treatment treatment) {
        repository.save(treatment);
    }

    public List<Treatment> getAll() {
        return repository.findAll();
    }

    public void update(Integer id, Treatment treatment) {
        Treatment oldTreatment = repository.findTreatmentById(id);
        treatment.setId(oldTreatment.getId());
        repository.save(treatment);
    }

    public void delete(Integer id) {
        repository.delete(repository.findTreatmentById(id));
    }
    public boolean delete(Treatment treatment) {
        if (repository.findById(treatment.getId()).isPresent()) {
            repository.deleteById(treatment.getId());
            return true;
        } return false;
    }

    public List<Treatment> find(Integer vetclinicID) {
        return repository.findAllByVetclinic_Id(vetclinicID);
    }

}
