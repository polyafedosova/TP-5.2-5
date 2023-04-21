package ru.vsu.dogapp.service;

import org.springframework.stereotype.Service;
import ru.vsu.dogapp.dto.TreatmentDto;
import ru.vsu.dogapp.entity.Treatment;
import ru.vsu.dogapp.mapper.TreatmentMapper;
import ru.vsu.dogapp.repository.TreatmentRepository;
import ru.vsu.dogapp.repository.VetclinicRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentService {

    private final TreatmentRepository repository;
    private final VetclinicRepository vetclinicRepository;
    private final TreatmentMapper mapper;

    public TreatmentService(TreatmentRepository repository, VetclinicRepository vetclinicRepository, TreatmentMapper mapper) {
        this.repository = repository;
        this.vetclinicRepository = vetclinicRepository;
        this.mapper = mapper;
    }

    public void save(Integer vetclinicId, TreatmentDto treatmentDto) {
        Treatment treatment = mapper.toEntity(treatmentDto);
        treatment.setVetclinic(vetclinicRepository.findVetclinicById(vetclinicId));
        repository.save(treatment);
    }

    public List<Treatment> getAll() {
        return repository.findAll();
    }

    public void update(Integer id, TreatmentDto treatmentDto) {
        Treatment oldTreatment = repository.findTreatmentById(id);
        Treatment treatment = mapper.toEntity(treatmentDto);
        treatment.setId(oldTreatment.getId());
        treatment.setVetclinic(oldTreatment.getVetclinic());
        repository.save(treatment);
    }

    public void delete(Integer id) {
        repository.delete(repository.findTreatmentById(id));
    }
    public List<TreatmentDto> find(Integer vetclinicID) {
        return repository.findAllByVetclinic_Id(vetclinicID)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
