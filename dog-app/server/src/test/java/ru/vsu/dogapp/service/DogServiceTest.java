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
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.entity.type.Role;
import ru.vsu.dogapp.mapper.DogMapper;
import ru.vsu.dogapp.repository.DogRepository;
import ru.vsu.dogapp.repository.OwnerRepository;

import javax.transaction.Transactional;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;

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
    void saveTest() {
        //given
        DogDto dogDto = new DogDto(1,"Artem","2002-06-02",true,"FKn");
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        ownerRepository.save(owner);

        service.save(owner.getUsername(), dogDto);
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
    void updateTest() {
        //given
        DogDto oldDogDto = new DogDto(1,"Artem","2002-06-02",true,"FKn");
        DogDto newDogDto = new DogDto(2,"Kirill","2002-06-02",true,"FKn");
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        ownerRepository.save(owner);
        service.save(owner.getUsername(), oldDogDto);
        service.update(1,newDogDto);

        //when
        var response = repository.findDogById(1);

        //then
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(newDogDto.getName())));
        assertThat(response.getBreed(), is(equalTo(newDogDto.getBreed())));
        assertThat(response.getBirthday(), is(equalTo(newDogDto.getBirthday())));
        assertThat(response.getSex(), is(equalTo(newDogDto.getSex())));
        assertThat(response.getOwner().getName(), is(equalTo(owner.getName())));
    }


    @Transactional
    @Rollback
    @Test
    void deleteTest() {
        //given
        DogDto dogDto = new DogDto(1,"Artem","2002-06-02",true,"FKn");
        DogDto newDogDto = new DogDto(2,"Kirill","2002-06-02",true,"FKn");
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        ownerRepository.save(owner);


        service.save(owner.getUsername(), dogDto);
        service.save(owner.getUsername(), newDogDto);
        //when

        service.delete(dogDto.getId());
        var response = repository.findDogById(newDogDto.getId());
        //then

        assertThat(repository.findById(dogDto.getId()), is(Optional.empty()));
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(newDogDto.getName())));
        assertThat(response.getBreed(), is(equalTo(newDogDto.getBreed())));
        assertThat(response.getBirthday(), is(equalTo(newDogDto.getBirthday())));
        assertThat(response.getSex(), is(equalTo(newDogDto.getSex())));
    }


    @Transactional
    @Rollback
    @Test
    void getByOwner() {
        
    }
}