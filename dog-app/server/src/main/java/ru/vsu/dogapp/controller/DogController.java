package ru.vsu.dogapp.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vsu.dogapp.dto.DogDto;
import ru.vsu.dogapp.service.DogService;

import javax.validation.Valid;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/owner/{username}/dogs")
public class DogController {

    private final DogService service;

    public DogController(DogService service) {
        this.service = service;
    }

    @PostMapping("/new")
    @ApiOperation("Saving information about a new dog")
    public void saveNewDog(@PathVariable String username, @Valid @RequestBody DogDto dog) {
        try {
            service.save(username, dog);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format", e);
        }
    }

    @PutMapping("/{dog_id}/update")
    @ApiOperation("Updating information about a dog")
    public void updateDog(@PathVariable Integer dog_id, @Valid @RequestBody DogDto dog, @PathVariable String username) {
        try {
            service.update(dog_id, dog);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format", e);
        }
    }

    @DeleteMapping("/{dog_id}/delete")
    @ApiOperation("Deleting information about a dog")
    public void deleteDog(@PathVariable Integer dog_id, @PathVariable String username) {
        service.delete(dog_id);
    }

    @GetMapping()
    @ApiOperation("Getting a list of all owner's dogs")
    public List<DogDto> getDogs(@PathVariable String username) {
        return service.getByOwner(username);
    }
}
