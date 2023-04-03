package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.entity.Vetclinic;
import ru.vsu.dogapp.service.VetclinicService;

import java.util.List;

@RestController
public class VetclinicController {

    private final VetclinicService service;

    public VetclinicController(VetclinicService service) {
        this.service = service;
    }

    @GetMapping("/vetclinics")
    public List<Vetclinic> getAllVetclinic() {
        return service.getAll();
    }
    @PostMapping("/vetclinics/new")
    public void saveNewVetclinic(@RequestBody Vetclinic vetclinic) {
        service.save(vetclinic);
    }
    @PutMapping("/vetclinics/update/{id}")
    public void updateVetclinic(@PathVariable Integer id, @RequestBody Vetclinic vetclinic) {
        service.update(id, vetclinic);
    }
    @DeleteMapping("/vetclinics/delete/{id}")
    public void deleteVetclinic(@PathVariable Integer id) {
        service.delete(id);
    }
}
