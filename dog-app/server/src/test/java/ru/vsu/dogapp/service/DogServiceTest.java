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
import ru.vsu.dogapp.dto.OwnerDto;
import ru.vsu.dogapp.entity.Dog;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.entity.type.Role;
import ru.vsu.dogapp.mapper.DogMapper;
import ru.vsu.dogapp.repository.DogRepository;
import ru.vsu.dogapp.repository.OwnerRepository;

import javax.transaction.Transactional;

import java.util.Collections;

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
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        ownerRepository.save(owner);

        service.save("Posix", dogDto);
        // when
        var response = repository.findDogById(1);
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(dogDto.getName())));
        assertThat(response.getBreed(), is(equalTo(dogDto.getBreed())));
        assertThat(response.getBirthday(), is(equalTo(dogDto.getBirthday())));
        assertThat(response.getSex(), is(equalTo(dogDto.getSex())));
        assertThat(response.getOwner().getName(), is(equalTo(owner.getName())));
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