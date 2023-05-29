package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.VetSimpleDto;
import ru.vsu.dogapp.dto.VetclinicDto;
import ru.vsu.dogapp.service.VetclinicService;

import javax.validation.Valid;
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
    @GetMapping("/{id}")
    public VetclinicDto find(@PathVariable Integer id) {
        return service.find(id);
    }
    @PostMapping("/edit/new")
    public void saveNewVetclinic(@Valid  @RequestBody VetclinicDto vetclinic) {
        service.save(vetclinic);
    }
    @PutMapping("/edit/{id}/update")
    public void updateVetclinic(@PathVariable Integer id, @Valid @RequestBody VetclinicDto vetclinic) {
        service.update(id, vetclinic);
    }
    @DeleteMapping("/edit/{id}/delete")
    public void deleteVetclinic(@PathVariable Integer id) {
        service.delete(id);
    }
    @PostMapping("/sort")
    public List<VetSimpleDto> sort(String treatment, String city) {
        return service.sort(treatment, city);
    }
    @PostMapping("/findByCity")
    public List<VetclinicDto> findByCity(String city) {
        return service.findByCity(city);
    }
}
