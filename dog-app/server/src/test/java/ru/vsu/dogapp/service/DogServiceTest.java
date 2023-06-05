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
import java.util.List;
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
        DogDto newDogDto = new DogDto(2,"Kirill","2003-05-02",true,"FKn");
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        ownerRepository.save(owner);
        service.save(owner.getUsername(), oldDogDto);
        service.update(3,newDogDto);

        //when
        var response = repository.findDogById(3);

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
        DogDto newDogDto = new DogDto(2,"Kirill","2003-04-02",true,"FKN");
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        ownerRepository.save(owner);
        service.save(owner.getUsername(), dogDto);
        service.save(owner.getUsername(), newDogDto);
        //when
        service.delete(4);
        var response = repository.findDogById(5);
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
    void getByOwnerTest() {
        DogDto dogDto1 = new DogDto(1,"Artem","2002-06-02",true,"FKn");
        DogDto dogDto2 = new DogDto(2,"Kirill","2001-05-10",true,"FKn");

        Owner owner1 = new Owner(4,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        Owner owner2 = new Owner(5,"Makson", "Abcd123*@", "Maks", true, Collections.singleton(Role.USER));
        ownerRepository.save(owner1);
        ownerRepository.save(owner2);

        service.save(owner1.getUsername(), dogDto1);
        service.save(owner1.getUsername(), dogDto2);
        // when
        List<DogDto> list = service.getByOwner(owner1.getUsername());
        // then
        assertThat(list.get(0), is(notNullValue()));
        assertThat(list.get(0).getName(), is(equalTo(dogDto2.getName())));
        assertThat(list.get(0).getBreed(), is(equalTo(dogDto2.getBreed())));
        assertThat(list.get(0).getBirthday(), is(equalTo(dogDto2.getBirthday())));
        assertThat(list.get(0).getSex(), is(equalTo(dogDto2.getSex())));
    }

}