package ru.vsu.dogapp.service;

import org.springframework.stereotype.Service;
import ru.vsu.dogapp.dto.VetclinicDto;
import ru.vsu.dogapp.entity.Vetclinic;
import ru.vsu.dogapp.mapper.VetclinicMapper;
import ru.vsu.dogapp.repository.VetclinicRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VetclinicService {

    private final VetclinicRepository repository;
    private final VetclinicMapper mapper;

    public VetclinicService(VetclinicRepository repository, VetclinicMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void save(VetclinicDto vetclinicDto) {
        Vetclinic vetclinic = mapper.toEntity(vetclinicDto);
        repository.save(vetclinic);
    }

    public List<VetclinicDto> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void update(Integer id, VetclinicDto vetclinicDto) {
        Vetclinic oldVetclinic = repository.findVetclinicById(id);
        Vetclinic vetclinic = mapper.toEntity(vetclinicDto);
        vetclinic.setId(oldVetclinic.getId());
        repository.save(vetclinic);
    }

    public void delete(Integer id) {
        repository.delete(repository.findVetclinicById(id));
    }

    public VetclinicDto find(Integer id) {
        return mapper.toDto(repository.findVetclinicById(id));
    }
}
