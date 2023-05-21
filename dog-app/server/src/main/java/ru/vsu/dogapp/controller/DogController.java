package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.DogDto;
import ru.vsu.dogapp.service.DogService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/owner/{username}/dogs")
public class DogController {

    private final DogService service;

    public DogController(DogService service) {
        this.service = service;
    }

    @PostMapping("/new")
    public void saveNewDog(@PathVariable String username, @Valid @RequestBody DogDto dog) {
        System.out.println(dog);
        service.save(username, dog);
    }
    @PutMapping("/{id}/update")
    public void updateDog(@PathVariable Integer id, @Valid @RequestBody DogDto dog, @PathVariable String username) {
        service.update(id, dog);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteDog(@PathVariable Integer id, @PathVariable String username) {
        service.delete(id);
    }
    @GetMapping()
    public List<DogDto> getDogs(@PathVariable String username) {
        return service.getByOwner(username);
    }
}
