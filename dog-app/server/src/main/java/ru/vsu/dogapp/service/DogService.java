package ru.vsu.dogapp.service;

import org.springframework.stereotype.Service;
import ru.vsu.dogapp.entity.Dog;
import ru.vsu.dogapp.repository.DogRepository;

import java.util.List;

@Service
public class DogService {

    private final DogRepository repository;

    public DogService(DogRepository repository) {
        this.repository = repository;
    }

    public void save(Dog dog) {
        repository.save(dog);
    }

    public List<Dog> getAll() {
        return repository.findAll();
    }

    public void update(Integer id, Dog dog) {
        Dog oldDog = repository.findDogById(id);
        dog.setId(oldDog.getId());
        repository.save(dog);
    }

    public void delete(Integer id) {
        repository.delete(repository.findDogById(id));
    }
    public boolean delete(Dog dog) {
        if (repository.findById(dog.getId()).isPresent()) {
            repository.deleteById(dog.getId());
            return true;
        } return false;
    }

    public List<Dog> find(Integer ownerID) {
        return repository.findAllByOwner_Id(ownerID);
    }
}
