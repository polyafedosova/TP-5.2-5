package ru.vsu.dogapp.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.TreatmentDto;
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
    @ApiOperation("Saving information about a new treatment")
    public void saveNewTreatment(@PathVariable Integer vetclinic_id, @Valid @RequestBody TreatmentDto treatment) {
        service.save(vetclinic_id, treatment);
    }
    @PutMapping("/edit/{treatment_id}/update")
    @ApiOperation("Updating information about a new treatment")
    public void updateTreatment(@PathVariable Integer treatment_id, @Valid @RequestBody TreatmentDto treatment) {
        service.update(treatment_id, treatment);
    }
    @DeleteMapping("/edit/{treatment_id}/delete")
    @ApiOperation("Deleting information about a new treatment")
    public void deleteTreatment(@PathVariable Integer treatment_id) {
        service.delete(treatment_id);
    }
    @GetMapping()
    @ApiOperation("Getting a list of all vet clinic's treatments")
    public List<TreatmentDto> getTreatments(@PathVariable Integer vetclinic_id) {
        return service.getByVentclinic(vetclinic_id);
    }
}
