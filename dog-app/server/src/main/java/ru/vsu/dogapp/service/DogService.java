package ru.vsu.dogapp.service;

import org.springframework.stereotype.Service;
import ru.vsu.dogapp.dto.DogDto;
import ru.vsu.dogapp.entity.Dog;
import ru.vsu.dogapp.mapper.DogMapper;
import ru.vsu.dogapp.repository.DogRepository;
import ru.vsu.dogapp.repository.OwnerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogService {

    private final DogRepository repository;
    private final OwnerRepository ownerRepository;
    private final DogMapper mapper;

    public DogService(DogRepository repository, OwnerRepository ownerRepository, DogMapper mapper) {
        this.repository = repository;
        this.ownerRepository = ownerRepository;
        this.mapper = mapper;
    }

    public void save(String username, DogDto dogDto) {
        Dog dog = mapper.toEntity(dogDto);
        dog.setOwner(ownerRepository.findByUsername(username));
        repository.save(dog);
    }

    public void update(Integer id, DogDto dogDto) {
        Dog oldDog = repository.findDogById(id);
        Dog dog = mapper.toEntity(dogDto);
        dog.setId(oldDog.getId());
        dog.setOwner(oldDog.getOwner());
        repository.save(dog);
    }

    public void delete(Integer id) {
        repository.delete(repository.findDogById(id));
    }

    public List<DogDto> getByOwner(String username) {
        return repository.findAllByOwner_Username(username)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
