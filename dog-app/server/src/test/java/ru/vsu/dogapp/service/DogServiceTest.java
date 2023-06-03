package ru.vsu.dogapp.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vsu.dogapp.IntegrationEnvironment;
import ru.vsu.dogapp.dto.DogDto;
import ru.vsu.dogapp.entity.Dog;
import ru.vsu.dogapp.mapper.DogMapper;
import ru.vsu.dogapp.repository.DogRepository;
import ru.vsu.dogapp.repository.OwnerRepository;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class DogServiceTest extends IntegrationEnvironment {
    @Autowired
    private DogRepository repository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private DogMapper mapper;

    @Autowired
    private DogService service;

    @Transactional
    @Rollback
    @Test
    void save() {
        //given
        DogDto dogDto = new DogDto(1,"Artem","2002-06-02",true,"FKn");
        Dog dog = mapper.toEntity(dogDto);

        repository.saveAndFlush(dog);

        // when
        var response = repository.findDogById(1);

        // then
        assertThat(response, is(notNullValue()));

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
    void getByOwner() {
    }
}