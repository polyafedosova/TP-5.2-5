package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.entity.Dog;
import ru.vsu.dogapp.service.DogService;

import java.util.List;

@RestController
public class DogController {

    private final DogService service;

    public DogController(DogService service) {
        this.service = service;
    }

    @GetMapping("/dogs")
    public List<Dog> getAllDogs() {
        return service.getAll();
    }
    @PostMapping("/dogs/new")
    public void saveNewDog(@RequestBody Dog dog) {
        service.save(dog);
    }
    @PutMapping("/dogs/update/{id}")
    public void updateDog(@PathVariable Integer id, @RequestBody Dog dog) {
        service.update(id, dog);
    }
    @DeleteMapping("/dogs/delete/{id}")
    public void deleteDog(@PathVariable Integer id) {
        service.delete(id);
    }
    @GetMapping("/owner/{id}/dogs")
    public List<Dog> getDogsOwner(@PathVariable Integer id) {
        return service.find(id);
    }
}
