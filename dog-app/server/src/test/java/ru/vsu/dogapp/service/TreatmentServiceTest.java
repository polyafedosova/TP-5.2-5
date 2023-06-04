package ru.vsu.dogapp.service;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vsu.dogapp.IntegrationEnvironment;
import ru.vsu.dogapp.dto.DogDto;
import ru.vsu.dogapp.dto.EventDto;
import ru.vsu.dogapp.dto.TreatmentDto;
import ru.vsu.dogapp.dto.VetclinicDto;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.entity.Treatment;
import ru.vsu.dogapp.entity.Vetclinic;
import ru.vsu.dogapp.entity.type.Role;
import ru.vsu.dogapp.mapper.TreatmentMapper;
import ru.vsu.dogapp.repository.TreatmentRepository;
import ru.vsu.dogapp.repository.VetclinicRepository;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class TreatmentServiceTest extends IntegrationEnvironment {
    @Autowired
    private TreatmentRepository repository;
    @Autowired
    private VetclinicRepository vetclinicRepository;
    @Autowired
    private TreatmentMapper mapper;
    @Autowired
    private TreatmentService service;

    @Transactional
    @Rollback
    @Test
    void save() {
        //given
        Vetclinic vetclinic = new Vetclinic("Vet", "89087803328","vet","Russia","Воронежская область", "Voronezh", "kor", "1");
        Treatment treatment = new Treatment(0,"name", new BigDecimal(10000),vetclinic);
        vetclinicRepository.save(vetclinic);
        repository.findAllByVetclinic_Id(1);
        service.save(1, mapper.toDto(treatment));

        // when
        var response = repository.findAll();
        // then
        assertThat(response.get(0), is(notNullValue()));
        assertThat(response.get(0).getName(), is(equalTo(treatment.getName())));
        assertThat(response.get(0).getPrice(), is(equalTo(treatment.getPrice())));
    }
    @Transactional
    @Rollback
    @Test
    void update() {
        //given
        Vetclinic vetclinic = new Vetclinic("Vet", "89087803328","vet","Russia","Воронежская область", "Voronezh", "kor", "1");
        Treatment treatment = new Treatment(2,"name", new BigDecimal(10000),vetclinic);
        Treatment treatment1 = new Treatment(3,"name1", new BigDecimal(1010),vetclinic);
        vetclinicRepository.save(vetclinic);
        service.save(1,mapper.toDto(treatment));
        service.update(1,mapper.toDto(treatment1));

        //when
        var response = repository.findTreatmentById(1);

        //then
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(treatment1.getName())));
        assertThat(response.getPrice(), is(equalTo(treatment1.getPrice())));
        assertThat(response.getVetclinic().getName(), is(equalTo(treatment1.getVetclinic().getName())));

    }
    @Transactional
    @Rollback
    @Test
    void delete() {
        //given
        Vetclinic vetclinic = new Vetclinic("Vet", "89087803328","vet","Russia","Воронежская область", "Voronezh", "kor", "1");
        Treatment treatment = new Treatment(2,"name", new BigDecimal(10000),vetclinic);
        Treatment treatment1 = new Treatment(3,"name1", new BigDecimal(1010),vetclinic);
        vetclinicRepository.save(vetclinic);

        service.save(1,mapper.toDto(treatment));
        service.save(1,mapper.toDto(treatment1));


        //when
        var response = repository.findTreatmentById(2);

        //then
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(treatment1.getName())));
        assertThat(response.getPrice(), is(equalTo(treatment1.getPrice())));
        assertThat(response.getVetclinic().getName(), is(equalTo(treatment1.getVetclinic().getName())));

    }
    @Transactional
    @Rollback
    @Test
    void getByVentclinic() {
    }
}