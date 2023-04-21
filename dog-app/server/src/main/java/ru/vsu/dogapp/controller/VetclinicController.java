package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.VetclinicDto;
import ru.vsu.dogapp.entity.Vetclinic;
import ru.vsu.dogapp.service.VetclinicService;

import java.util.List;

@RestController
@RequestMapping("/vetclinics")
public class VetclinicController {

    private final VetclinicService service;

    public VetclinicController(VetclinicService service) {
        this.service = service;
    }

    @GetMapping()
    public List<VetclinicDto> getAllVetclinic() {
        return service.getAll();
    }
    @PostMapping("/new")
    public void saveNewVetclinic(@RequestBody VetclinicDto vetclinic) {
        service.save(vetclinic);
    }
    @PutMapping("/{id}/update")
    public void updateVetclinic(@PathVariable Integer id, @RequestBody VetclinicDto vetclinic) {
        service.update(id, vetclinic);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteVetclinic(@PathVariable Integer id) {
        service.delete(id);
    }
}
