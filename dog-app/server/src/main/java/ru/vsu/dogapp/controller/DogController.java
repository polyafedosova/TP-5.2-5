package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.DogDto;
import ru.vsu.dogapp.entity.Dog;
import ru.vsu.dogapp.service.DogService;

import java.util.List;

@RestController
@RequestMapping("/owner/{owner_id}/dogs")
public class DogController {

    private final DogService service;

    public DogController(DogService service) {
        this.service = service;
    }

    @PostMapping("/new")
    public void saveNewDog(@PathVariable Integer owner_id, @RequestBody DogDto dog) {
        service.save(owner_id, dog);
    }
    @PutMapping("/{id}/update")
    public void updateDog(@PathVariable Integer id, @RequestBody DogDto dog) {
        service.update(id, dog);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteDog(@PathVariable Integer id) {
        service.delete(id);
    }
    @GetMapping()
    public List<DogDto> getDogsOwner(@PathVariable Integer owner_id) {
        return service.find(owner_id);
    }
}
