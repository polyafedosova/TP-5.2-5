package ru.vsu.dogapp.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vsu.dogapp.IntegrationEnvironment;
import ru.vsu.dogapp.dto.EventDto;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.entity.type.Role;
import ru.vsu.dogapp.mapper.EventMapper;
import ru.vsu.dogapp.repository.EventRepository;
import ru.vsu.dogapp.repository.OwnerRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class EventServiceTest extends IntegrationEnvironment {

    @Autowired
    private EventRepository repository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private EventMapper mapper;
    @Autowired
    private EventService service;

    @Transactional
    @Rollback
    @Test
    void saveTest() {
        //given
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        EventDto eventDto = new EventDto(null,"Name", "2023-07-23", "20:30:00", "name");

        ownerRepository.save(owner);

        service.save(owner.getUsername(), eventDto);
        // when
        var response = repository.findEventById(1);
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(eventDto.getName())));
        assertThat(response.getDate(), is(equalTo(eventDto.getDate())));
        assertThat(response.getTime(), is(equalTo(eventDto.getTime())));
        assertThat(response.getDescription(), is(equalTo(eventDto.getDescription())));
        assertThat(response.getOwner().getName(), is(equalTo(owner.getName())));
    }
    @Transactional
    @Rollback
    @Test
    void updateTest() {
        //given

        EventDto eventDto1 = new EventDto(null,"Name", "2023-07-23", "20:30:00", "name");
        EventDto eventDto2 = new EventDto(null,"Name1", "2032-01-13", "20:40:10", "name1");
        Owner owner = new Owner(null,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        ownerRepository.save(owner);

        service.save(owner.getUsername(), eventDto1);
        service.update(4,eventDto2);
        // when
        var response = repository.findEventById(4);
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(eventDto2.getName())));
        assertThat(response.getDate(), is(equalTo(eventDto2.getDate())));
        assertThat(response.getTime(), is(equalTo(eventDto2.getTime())));
        assertThat(response.getDescription(), is(equalTo(eventDto2.getDescription())));
        assertThat(response.getOwner().getName(), is(equalTo(owner.getName())));
    }
    @Transactional
    @Rollback
    @Test
    void deleteTest() {
        Owner owner = new Owner(3,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        EventDto eventDto1 = new EventDto(null,"Name", "2023-07-23", "20:30:00", "name");
        EventDto eventDto2 = new EventDto(null,"Name1", "2032-01-13", "20:40:10", "name1");
        ownerRepository.save(owner);

        service.save(owner.getUsername(), eventDto1);
        service.save(owner.getUsername(),eventDto2);
        // when
        service.delete(5);
        var response = repository.findEventById(6);
        // then
        assertThat(repository.findById(5), is(Optional.empty()));
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(eventDto2.getName())));
        assertThat(response.getDate(), is(equalTo(eventDto2.getDate())));
        assertThat(response.getTime(), is(equalTo(eventDto2.getTime())));
        assertThat(response.getDescription(), is(equalTo(eventDto2.getDescription())));
        assertThat(response.getOwner().getName(), is(equalTo(owner.getName())));

    }
    @Transactional
    @Rollback
    @Test
    void getByOwnerTest() {
        Owner owner = new Owner(2,"Posix", "Abcd123*@", "Polina", true, Collections.singleton(Role.USER));
        Owner owner1 = new Owner(3,"Makson", "Abcd123*@", "Maks", true, Collections.singleton(Role.USER));
        EventDto eventDto1 = new EventDto(null,"Name", "2023-07-23", "20:30:00", "name");
        EventDto eventDto2 = new EventDto(null,"Name1", "2032-01-13", "20:40:10", "name1");
        ownerRepository.save(owner);
        ownerRepository.save(owner1);

        service.save(owner.getUsername(), eventDto1);
        service.save(owner1.getUsername(),eventDto2);
        // when

        List<EventDto> list = service.getByOwner(owner.getUsername());
        System.out.println(list);
        // then
        assertThat(list.get(0), is(notNullValue()));
        assertThat(list.get(0).getName(), is(equalTo(eventDto1.getName())));
        assertThat(list.get(0).getDate(), is(equalTo(eventDto1.getDate())));
        assertThat(list.get(0).getTime(), is(equalTo(eventDto1.getTime())));
        assertThat(list.get(0).getDescription(), is(equalTo(eventDto1.getDescription())));
    }
}