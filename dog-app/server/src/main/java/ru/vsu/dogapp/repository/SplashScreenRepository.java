package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.SplashScreen;

import java.util.List;

@Repository
public interface SplashScreenRepository extends JpaRepository<SplashScreen, Integer> {

    @NonNull
    List<SplashScreen> findAll();

    SplashScreen findSplashScreenById(Integer id);
}
