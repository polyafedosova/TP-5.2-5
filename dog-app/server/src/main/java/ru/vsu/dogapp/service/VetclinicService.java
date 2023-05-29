package ru.vsu.dogapp.service;

import org.springframework.stereotype.Service;
import ru.vsu.dogapp.dto.VetSimpleDto;
import ru.vsu.dogapp.dto.VetclinicDto;
import ru.vsu.dogapp.entity.Treatment;
import ru.vsu.dogapp.entity.Vetclinic;
import ru.vsu.dogapp.mapper.VetclinicMapper;
import ru.vsu.dogapp.repository.VetclinicRepository;

import java.math.BigDecimal;
import java.util.*;
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

    public List<VetSimpleDto> sort(String treatment, String city) {
        List<VetSimpleDto> sortList = new ArrayList<>();

        for (Vetclinic v: repository.findAll()) {
            if (city == null || city.isEmpty() || v.getCity().toLowerCase().contains(city.toLowerCase())) {
                BigDecimal min = null;
                for (Treatment t : v.getTreatments()) {
                    if (treatment == null || treatment.isEmpty() || t.getName().toLowerCase().contains(treatment.toLowerCase())) {
                        BigDecimal price = t.getPrice();
                        if (min == null || price.compareTo(min) < 0) {
                            min = price;
                        }
                    }
                }
                if (min != null) {
                    sortList.add(new VetSimpleDto(mapper.toDto(v), min));
                }
            }
        }
        sortList.sort(Comparator.comparing(VetSimpleDto::getMinPrice));
        return sortList;
    }

    public List<VetclinicDto> findByCity(String city) {
        return repository.findAllByCity(city)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
