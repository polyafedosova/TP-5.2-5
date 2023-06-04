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
import ru.vsu.dogapp.dto.EventDto;
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
        Vetclinic vetclinic = new Vetclinic("Vet", "89087803328","vet","Russia","Воронежская область", "Voronezh", "kor", "1");
        Treatment treatment = new Treatment(0,"name", new BigDecimal(10000),vetclinic);
        



        System.out.println(vetclinicRepository.findAll());

    }
    @Transactional
    @Rollback
    @Test
    void update() {
    }
    @Transactional
    @Rollback
    @Test
    void delete() {
    }
    @Transactional
    @Rollback
    @Test
    void getByVentclinic() {
    }
}