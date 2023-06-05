package ru.vsu.dogapp.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vsu.dogapp.IntegrationEnvironment;
import ru.vsu.dogapp.entity.Vetclinic;
import ru.vsu.dogapp.mapper.VetclinicMapper;
import ru.vsu.dogapp.repository.VetclinicRepository;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class VetclinicServiceTest extends IntegrationEnvironment {
    @Autowired
    private VetclinicRepository repository;
    @Autowired
    private VetclinicMapper mapper;
    @Autowired
    private VetclinicService service;

    @Transactional
    @Rollback
    @Test
    void saveTest() {
        //given
        Vetclinic vetclinic = new Vetclinic("Vet", "89087803328","vet","Russia","Воронежская область", "Voronezh", "kor", "1");
        service.save(mapper.toDto(vetclinic));
        // when
        System.out.println(repository.findAll());
        var response = repository.findVetclinicById(1);
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(vetclinic.getName())));
        assertThat(response.getPhone(), is(equalTo(vetclinic.getPhone())));
        assertThat(response.getCountry(), is(equalTo(vetclinic.getCountry())));
    }

    @Transactional
    @Rollback
    @Test
    void getAllTest() {
        Vetclinic vetclinic = new Vetclinic("Vet", "89087803328","vet","Russia","Воронежская область", "Voronezh", "kor", "1");
        Vetclinic vetclinic1 = new Vetclinic("Vet1", "89087803331","vet1","Russia2","Воронежская", "Voronez", "kor2", "11");
        service.save(mapper.toDto(vetclinic));
        service.save(mapper.toDto(vetclinic1));
        // when
        var response = service.getAll();
        // then
        assertThat(response.get(0), is(notNullValue()));
        assertThat(response.get(0).getName(), is(equalTo(vetclinic.getName())));
        assertThat(response.get(0).getPhone(), is(equalTo(vetclinic.getPhone())));
        assertThat(response.get(0).getCountry(), is(equalTo(vetclinic.getCountry())));
        assertThat(response.get(1), is(notNullValue()));
        assertThat(response.get(1).getName(), is(equalTo(vetclinic1.getName())));
        assertThat(response.get(1).getPhone(), is(equalTo(vetclinic1.getPhone())));
        assertThat(response.get(1).getCountry(), is(equalTo(vetclinic1.getCountry())));
    }

    @Transactional
    @Rollback
    @Test
    void updateTest() {
        //given
        Vetclinic vetclinic = new Vetclinic("Vet", "89087803328","vet","Russia","Воронежская область", "Voronezh", "kor", "1");
        Vetclinic vetclinic1 = new Vetclinic("Vet1", "89087803331","vet1","Russia2","Воронежская", "Voronez", "kor2", "11");
        service.save(mapper.toDto(vetclinic));
        System.out.println(repository.findAll());
        service.update(4,mapper.toDto(vetclinic1));
        // when
        var response = repository.findVetclinicById(4);
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(vetclinic1.getName())));
        assertThat(response.getPhone(), is(equalTo(vetclinic1.getPhone())));
        assertThat(response.getCountry(), is(equalTo(vetclinic1.getCountry())));
    }

    @Transactional
    @Rollback
    @Test
    void deleteTest() {
        //given
        Vetclinic vetclinic = new Vetclinic("Vet", "89087803328","vet","Russia","Воронежская область", "Voronezh", "kor", "1");
        Vetclinic vetclinic1 = new Vetclinic("Vet1", "89087803331","vet1","Russia2","Воронежская", "Voronez", "kor2", "11");
        service.save(mapper.toDto(vetclinic));
        service.save(mapper.toDto(vetclinic1));
        System.out.println(repository.findAll());
        service.delete(9);
        //when
        var response = repository.findVetclinicById(10);
        // then
        assertThat(repository.findVetclinicById(9), is(nullValue()));
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(vetclinic1.getName())));
        assertThat(response.getPhone(), is(equalTo(vetclinic1.getPhone())));
        assertThat(response.getCountry(), is(equalTo(vetclinic1.getCountry())));
    }

    @Transactional
    @Rollback
    @Test
    void findTest() {
        //given
        Vetclinic vetclinic = new Vetclinic("Vet", "89087803328","vet","Russia","Воронежская область", "Voronezh", "kor", "1");
        Vetclinic vetclinic1 = new Vetclinic("Vet1", "89087803331","vet1","Russia2","Воронежская", "Voronez", "kor2", "11");
        service.save(mapper.toDto(vetclinic));
        service.save(mapper.toDto(vetclinic1));
        // when
        System.out.println(repository.findAll());
        var response = service.find(2);
        // then
        assertThat(response, is(notNullValue()));
        assertThat(response.getName(), is(equalTo(vetclinic.getName())));
        assertThat(response.getPhone(), is(equalTo(vetclinic.getPhone())));
        assertThat(response.getCountry(), is(equalTo(vetclinic.getCountry())));
    }

    @Transactional
    @Rollback
    @Test
    void findByCityTest() {
        Vetclinic vetclinic = new Vetclinic("Vet", "89087803328","vet","Russia","Воронежская область", "Voronezh", "kor", "1");
        Vetclinic vetclinic1 = new Vetclinic("Vet1", "89087803331","vet1","Russia2","Воронежская", "Voronezh", "kor2", "11");
        Vetclinic vetclinic2 = new Vetclinic("Vet1", "89087803331","vet1","Russia2","Воронежская", "Voronez", "kor2", "11");
        service.save(mapper.toDto(vetclinic));
        service.save(mapper.toDto(vetclinic1));
        // when

        var response = service.findByCity("voronezh");
        // then
        assertThat(response.get(0), is(notNullValue()));
        assertThat(response.get(0).getName(), is(equalTo(vetclinic.getName())));
        assertThat(response.get(0).getPhone(), is(equalTo(vetclinic.getPhone())));
        assertThat(response.get(0).getCountry(), is(equalTo(vetclinic.getCountry())));
        assertThat(response.get(1), is(notNullValue()));
        assertThat(response.get(1).getName(), is(equalTo(vetclinic1.getName())));
        assertThat(response.get(1).getPhone(), is(equalTo(vetclinic1.getPhone())));
        assertThat(response.get(1).getCountry(), is(equalTo(vetclinic1.getCountry())));
    }
}