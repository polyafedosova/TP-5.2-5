package ru.vsu.dogapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.dogapp.entity.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>  {

    @NonNull
    List<Event> findAll();
    @Query("SELECT e FROM Event e WHERE e.owner.username = :username")
    List<Event> findAllByOwner_Username(@Param("username") String username);

    @Query("SELECT e FROM Event e WHERE e.id = :id")
    Event findEventById(@Param("id") Integer id);
}
