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
    void update() {
    }
    @Transactional
    @Rollback
    @Test
    void updatePassword() {
    }
    @Transactional
    @Rollback
    @Test
    void delete() {
    }
    @Transactional
    @Rollback
    @Test
    void find() {
    }
    @Transactional
    @Rollback
    @Test
    void changeShowAll() {
    }
    @Transactional
    @Rollback
    @Test
    void changeShow() {
    }
}