package ru.vsu.dogapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.dogapp.entity.SplashScreen;
import ru.vsu.dogapp.repository.SplashScreenRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SplashScreenService {

    private final SplashScreenRepository repository;
    private final OwnerService ownerService;

    public void save(SplashScreen splashScreen) {
        repository.save(splashScreen);
    }

    public List<SplashScreen> getAll() {
        return repository.findAll();
    }

    public void update(Integer id, SplashScreen splashScreen) {
        splashScreen.setId(id);
        repository.save(splashScreen);
    }

    public void delete(Integer id) {
        repository.delete(repository.findSplashScreenById(id));
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
