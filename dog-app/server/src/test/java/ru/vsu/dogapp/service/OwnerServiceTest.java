package ru.vsu.dogapp.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vsu.dogapp.IntegrationEnvironment;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.entity.type.Role;
import ru.vsu.dogapp.mapper.OwnerMapper;
import ru.vsu.dogapp.repository.DogRepository;
import ru.vsu.dogapp.repository.EventRepository;
import ru.vsu.dogapp.repository.OwnerRepository;

import javax.transaction.Transactional;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class OwnerServiceTest extends IntegrationEnvironment {
    @Autowired
    private  OwnerRepository repository;
    @Autowired
    private  DogRepository dogRepository;
    @Autowired
    private  EventRepository eventRepository;
    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private  OwnerMapper mapper;
    @Autowired
    private OwnerService service;


    @Transactional
    @Rollback
    @Test
    void loadUserByUsernameTest() {
        //given
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        repository.save(owner);

        // when
        var response = service.loadUserByUsername(owner.getUsername());
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getUsername(), is(equalTo(owner.getUsername())));
        assertThat(response.getPassword(), is(equalTo(owner.getPassword())));

    }
    @Transactional
    @Rollback
    @Test
    void saveTest() {
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        boolean bool = service.save(mapper.toDto(owner));
        // when
        var response = repository.findByUsername(owner.getUsername());
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getUsername(), is(equalTo(owner.getUsername())));
        assertThat(response.getName(), is(equalTo(owner.getName())));
        assertThat(bool, is(equalTo(true)));
    }
    @Transactional
    @Rollback
    @Test
    void updateTest() {
        //given
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        Owner owner1 = new Owner(3,"Maxon", "Abcd123*@", "Max", true, Collections.singleton(Role.USER));

        repository.save(owner);
        service.update(owner.getUsername(),mapper.toDto(owner1));
        // when
        var response = repository.findByUsername(owner1.getUsername());
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getUsername(), is(equalTo(owner1.getUsername())));
        assertThat(response.getName(), is(equalTo(owner1.getName())));

    }
    @Transactional
    @Rollback
    @Test
    void updatePasswordTest() {
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));

        repository.save(owner);
        service.updatePassword(owner.getUsername(),"Abcd444*@");
        // when
        var response = repository.findByUsername(owner.getUsername());
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getUsername(), is(equalTo(owner.getUsername())));
        assertThat(response.getName(), is(equalTo(owner.getName())));
        assertThat(bCryptPasswordEncoder.matches("Abcd444*@", response.getPassword()), is(equalTo(true)));
    }
    @Transactional
    @Rollback
    @Test
    void deleteTest() {
        Owner owner = new Owner(null,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        Owner owner1 = new Owner(null,"Maxon", "Abcd123*@", "Max", true, Collections.singleton(Role.USER));

        service.save(mapper.toDto(owner));
        service.save(mapper.toDto(owner1));

        // when
        service.delete(owner.getUsername());
        var response = repository.findByUsername(owner1.getUsername());
        // then
        assertThat(repository.findByUsername(owner.getUsername()), is(nullValue()));
        assertThat(response, is(notNullValue()));
        assertThat(response.getUsername(), is(equalTo(owner1.getUsername())));
        assertThat(response.getName(), is(equalTo(owner1.getName())));
    }
    @Transactional
    @Rollback
    @Test
    void findTest() {
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));

        repository.save(owner);

        // when
        var response = service.find(owner.getUsername());
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getUsername(), is(equalTo(owner.getUsername())));
        assertThat(response.getName(), is(equalTo(owner.getName())));
    }
    @Transactional
    @Rollback
    @Test
    void changeShowAllTest() {

        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", false, Collections.singleton(Role.USER));
        Owner owner1 = new Owner(3,"Maxon", "Abcd123*@", "Max", false, Collections.singleton(Role.USER));
        repository.save(owner);
        repository.save(owner1);

        // when
        service.changeShowAll(true);
        var response = repository.findByUsername(owner.getUsername());
        var response1 = repository.findByUsername(owner1.getUsername());
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getUsername(), is(equalTo(owner.getUsername())));
        assertThat(response.getName(), is(equalTo(owner.getName())));
        assertThat(response.isShow(), is(equalTo(true)));

        assertThat(response, is(notNullValue()));
        assertThat(response1.getUsername(), is(equalTo(owner1.getUsername())));
        assertThat(response1.isShow(), is(equalTo(true)));
    }
    @Transactional
    @Rollback
    @Test
    void changeShowTest() {
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", false, Collections.singleton(Role.USER));
        repository.save(owner);

        // when
        service.changeShow(owner.getUsername(),true);
        var response = repository.findByUsername(owner.getUsername());
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getUsername(), is(equalTo(owner.getUsername())));
        assertThat(response.getName(), is(equalTo(owner.getName())));
        assertThat(response.isShow(), is(equalTo(true)));

    }
}