package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.OwnerDto;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.service.OwnerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService service;

    public OwnerController(OwnerService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Owner> getAllOwner() {
        return service.getAll();
    }
    @PostMapping("/new")
    public void saveNewOwner(@Valid  @RequestBody OwnerDto owner) {
        service.save(owner);
    }
    @PutMapping("/{id}/update")
    public void updateOwner(@PathVariable Integer id, @Valid @RequestBody OwnerDto owner) {
        service.update(id, owner);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteOwner(@PathVariable Integer id) {
        service.delete(id);
    }
}
