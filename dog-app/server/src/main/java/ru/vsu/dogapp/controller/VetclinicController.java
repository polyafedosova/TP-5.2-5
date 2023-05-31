package ru.vsu.dogapp.controller;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("Getting a list of all vet clinics")
    public List<VetclinicDto> getAllVetclinic() {
        return service.getAll();
    }
    @GetMapping("/{vetclinic_id}")
    @ApiOperation("Getting information about a vet clinic")
    public VetclinicDto find(@PathVariable Integer vetclinic_id) {
        return service.find(vetclinic_id);
    }
    @PostMapping("/edit/new")
    @ApiOperation("Saving information about a new vet clinic")
    public void saveNewVetclinic(@Valid  @RequestBody VetclinicDto vetclinic) {
        service.save(vetclinic);
    }
    @PutMapping("/edit/{vetclinic_id}/update")
    @ApiOperation("Updating information about a vet clinic")
    public void updateVetclinic(@PathVariable Integer vetclinic_id, @Valid @RequestBody VetclinicDto vetclinic) {
        service.update(vetclinic_id, vetclinic);
    }
    @DeleteMapping("/edit/{vetclinic_id}/delete")
    @ApiOperation("Deleting information about a vet clinic")
    public void deleteVetclinic(@PathVariable Integer vetclinic_id) {
        service.delete(vetclinic_id);
    }
    @PostMapping("/sort")
    @ApiOperation("Sorting a list of veterinary clinics by the price of the service")
    public List<VetSimpleDto> sort(String treatment, String city) {
        return service.sort(treatment, city);
    }
    @PostMapping("/findByCity")
    @ApiOperation("Filtering a list of vet clinics by city")
    public List<VetclinicDto> findByCity(String city) {
        return service.findByCity(city);
    }
}
