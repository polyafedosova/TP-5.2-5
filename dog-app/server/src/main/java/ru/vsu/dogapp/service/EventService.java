package ru.vsu.dogapp.service;

import org.springframework.stereotype.Service;
import ru.vsu.dogapp.dto.EventDto;
import ru.vsu.dogapp.entity.Event;
import ru.vsu.dogapp.mapper.EventMapper;
import ru.vsu.dogapp.repository.EventRepository;
import ru.vsu.dogapp.repository.OwnerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository repository;
    private final OwnerRepository ownerRepository;
    private final EventMapper mapper;

    public EventService(EventRepository repository, OwnerRepository ownerRepository, EventMapper mapper) {
        this.repository = repository;
        this.ownerRepository = ownerRepository;
        this.mapper = mapper;
    }

    public void save(String username, EventDto eventDto) {
        Event event = mapper.toEntity(eventDto);
        event.setOwner(ownerRepository.findByUsername(username));
        repository.save(event);
    }

    public void update(Integer id, EventDto eventDto) {
        Event oldEvent = repository.findEventById(id);
        Event event = mapper.toEntity(eventDto);
        event.setId(oldEvent.getId());
        event.setOwner(oldEvent.getOwner());
        repository.save(event);
    }

    public void delete(Integer id) {
        repository.delete(repository.findEventById(id));
    }

    public List<EventDto> getByOwner(String username) {
        return repository.findAllByOwner_Username(username)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
