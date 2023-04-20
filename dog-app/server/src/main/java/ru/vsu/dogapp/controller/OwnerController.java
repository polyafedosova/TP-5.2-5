package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.OwnerDto;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.service.OwnerService;

import java.util.List;

@RestController
public class OwnerController {

    private final OwnerService service;

    public OwnerController(OwnerService service) {
        this.service = service;
    }

    @GetMapping("/owners")
    public List<Owner> getAllOwner() {
        return service.getAll();
    }
    @PostMapping("/owners/new")
    public void saveNewOwner(@RequestBody OwnerDto owner) {
        service.save(owner);
    }
    @PutMapping("/owners/update/{id}")
    public void updateOwner(@PathVariable Integer id, @RequestBody Owner owner) {
        service.update(id, owner);
    }
    @DeleteMapping("/owners/delete/{id}")
    public void deleteOwner(@PathVariable Integer id) {
        service.delete(id);
    }
}
