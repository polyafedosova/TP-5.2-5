package ru.vsu.dogapp.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vsu.dogapp.IntegrationEnvironment;
import ru.vsu.dogapp.entity.SplashScreen;
import ru.vsu.dogapp.repository.SplashScreenRepository;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class SplashScreenServiceTest extends IntegrationEnvironment {
    @Autowired
    private SplashScreenRepository repository;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private SplashScreenService service;

    @Transactional
    @Rollback
    @Test
    void saveTest() {
        //given
        SplashScreen splashScreen = new SplashScreen(1,"title" , "text", new byte[1],"button");
        service.save(splashScreen);
        // when
        var response = service.getAll();
        // then
        assertThat(response.get(0), is(notNullValue()));
        assertThat(response.get(0).getTitle(), is(equalTo(splashScreen.getTitle())));
        assertThat(response.get(0).getText(), is(equalTo(splashScreen.getText())));
        assertThat(response.get(0).getButton(), is(equalTo(splashScreen.getButton())));
    }

    @Transactional
    @Rollback
    @Test
    void getAllTest() {
        //given
        SplashScreen splashScreen = new SplashScreen(1,"title" , "text", new byte[1],"button");
        SplashScreen splashScreen1 = new SplashScreen(2,"title1" , "tex1t", new byte[1],"button1");
        service.save(splashScreen);
        service.save(splashScreen1);
        // when
        var response = service.getAll();
        // then
        assertThat(response.get(0), is(notNullValue()));
        assertThat(response.get(0).getTitle(), is(equalTo(splashScreen.getTitle())));
        assertThat(response.get(0).getText(), is(equalTo(splashScreen.getText())));
        assertThat(response.get(0).getButton(), is(equalTo(splashScreen.getButton())));
        assertThat(response.get(1), is(notNullValue()));
        assertThat(response.get(1).getTitle(), is(equalTo(splashScreen1.getTitle())));
        assertThat(response.get(1).getText(), is(equalTo(splashScreen1.getText())));
        assertThat(response.get(1).getButton(), is(equalTo(splashScreen1.getButton())));
    }

    @Transactional
    @Rollback
    @Test
    void updateTest() {
        //given
        SplashScreen splashScreen = new SplashScreen(null,"title" , "text", new byte[1],"button");
        SplashScreen splashScreen1 = new SplashScreen(null,"title1" , "tex1t", new byte[1],"button1");
        service.save(splashScreen);
        service.update(3,splashScreen1);

        // when
        var response = service.getAll();
        // then
        assertThat(response.get(0), is(notNullValue()));
        assertThat(response.get(0).getTitle(), is(equalTo(splashScreen1.getTitle())));
        assertThat(response.get(0).getText(), is(equalTo(splashScreen1.getText())));
        assertThat(response.get(0).getButton(), is(equalTo(splashScreen1.getButton())));
    }

    @Transactional
    @Rollback
    @Test
    void deleteTest() {
        //given
        SplashScreen splashScreen = new SplashScreen(null,"title" , "text", new byte[1],"button");
        SplashScreen splashScreen1 = new SplashScreen(null,"title1" , "tex1t", new byte[1],"button1");
        service.save(splashScreen);
        service.save(splashScreen1);
        // when
        service.delete(6);
        var response = repository.findSplashScreenById(7);
        // then
        assertThat(repository.findSplashScreenById(6), is(nullValue()));
        assertThat(response, is(notNullValue()));
        assertThat(response.getTitle(), is(equalTo(splashScreen1.getTitle())));
        assertThat(response.getText(), is(equalTo(splashScreen1.getText())));
        assertThat(response.getButton(), is(equalTo(splashScreen1.getButton())));
    }

    @Transactional
    @Rollback
    @Test
    void deleteAllTest() {
        SplashScreen splashScreen = new SplashScreen(1,"title" , "text", new byte[1],"button");
        SplashScreen splashScreen1 = new SplashScreen(2,"title1" , "tex1t", new byte[1],"button1");
        service.save(splashScreen);
        service.save(splashScreen1);

        // when
        service.deleteAll();

        // then
        assertThat(repository.findSplashScreenById(1), is(nullValue()));
        assertThat(repository.findSplashScreenById(1), is(nullValue()));


    }
}