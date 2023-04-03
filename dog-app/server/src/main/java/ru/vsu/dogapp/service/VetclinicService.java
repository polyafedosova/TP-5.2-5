package ru.vsu.dogapp.service;

import org.springframework.stereotype.Service;
import ru.vsu.dogapp.entity.Vetclinic;
import ru.vsu.dogapp.repository.VetclinicRepository;

import java.util.List;

@Service
public class VetclinicService {

    private final VetclinicRepository repository;

    public VetclinicService(VetclinicRepository repository) {
        this.repository = repository;
    }

    public void save(Vetclinic vetclinic) {
        repository.save(vetclinic);
    }

    public List<Vetclinic> getAll() {
        return repository.findAll();
    }

    public void update(Integer id, Vetclinic vetclinic) {
        Vetclinic oldVetclinic = repository.findVetclinicById(id);
        vetclinic.setId(oldVetclinic.getId());
        repository.save(vetclinic);
    }

    public void delete(Integer id) {
        repository.delete(repository.findVetclinicById(id));
    }
    public boolean delete(Vetclinic vetclinic) {
        if (repository.findById(vetclinic.getId()).isPresent()) {
            repository.deleteById(vetclinic.getId());
            return true;
        } return false;
    }

    public Vetclinic find(Integer id) {
        return repository.findVetclinicById(id);
    }
}
