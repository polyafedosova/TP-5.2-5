package ru.vsu.dogapp.service;

import org.springframework.stereotype.Service;
import ru.vsu.dogapp.entity.Event;
import ru.vsu.dogapp.repository.EventRepository;

import java.util.List;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public void save(Event event) {
        repository.save(event);
    }

    public List<Event> getAll() {
        return repository.findAll();
    }

    public void update(Integer id, Event event) {
        Event oldEvent = repository.findEventById(id);
        event.setId(oldEvent.getId());
        repository.save(event);
    }

    public void delete(Integer id) {
        repository.delete(repository.findEventById(id));
    }
    public boolean delete(Event event) {
        if (repository.findById(event.getId()).isPresent()) {
            repository.deleteById(event.getId());
            return true;
        } return false;
    }

    public List<Event> find(Integer ownerID) {
        return repository.findAllByOwner_Id(ownerID);
    }
}
