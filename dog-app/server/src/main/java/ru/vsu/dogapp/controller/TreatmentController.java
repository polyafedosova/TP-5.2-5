package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.entity.Treatment;
import ru.vsu.dogapp.service.TreatmentService;
import java.util.List;

@RestController
public class TreatmentController {

    private final TreatmentService service;

    public TreatmentController(TreatmentService service) {
        this.service = service;
    }

    @GetMapping("/treatments")
    public List<Treatment> getAllTreatments() {
        return service.getAll();
    }
    @PostMapping("/treatments/new")
    public void saveNewTreatment(@RequestBody Treatment treatment) {
        service.save(treatment);
    }
    @PutMapping("/treatments/update/{id}")
    public void updateTreatment(@PathVariable Integer id, @RequestBody Treatment treatment) {
        service.update(id, treatment);
    }
    @DeleteMapping("/treatments/delete/{id}")
    public void deleteTreatment(@PathVariable Integer id) {
        service.delete(id);
    }
    @GetMapping("/vetclinic/{id}/treatments")
    public List<Treatment> getVetclinicTreatments(@PathVariable Integer id) {
        return service.find(id);
    }

}
