package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>  {

    @NonNull
    List<Event> findAll();
    List<Event> findAllByOwner_Id(Integer ownerID);
    Event findEventById(Integer id);
}
