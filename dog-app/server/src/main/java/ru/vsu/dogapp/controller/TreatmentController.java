package ru.vsu.dogapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.TreatmentDto;
import ru.vsu.dogapp.entity.Treatment;
import ru.vsu.dogapp.service.TreatmentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vetclinic/{vetclinic_id}/treatments")
public class TreatmentController {

    private final TreatmentService service;

    public TreatmentController(TreatmentService service) {
        this.service = service;
    }

    @PostMapping("/edit/new")
    public void saveNewTreatment(@PathVariable Integer vetclinic_id, @Valid @RequestBody TreatmentDto treatment) {
        service.save(vetclinic_id, treatment);
    }
    @PutMapping("/edit/{id}/update")
    public void updateTreatment(@PathVariable Integer id, @Valid @RequestBody TreatmentDto treatment) {
        service.update(id, treatment);
    }
    @DeleteMapping("/edit/{id}/delete")
    public void deleteTreatment(@PathVariable Integer id) {
        service.delete(id);
    }
    @GetMapping()
    public List<TreatmentDto> getVetclinicTreatments(@PathVariable Integer vetclinic_id) {
        return service.find(vetclinic_id);
    }
}
