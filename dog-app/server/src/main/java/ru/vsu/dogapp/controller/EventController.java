package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.entity.Event;
import ru.vsu.dogapp.service.EventService;

import java.util.List;

@RestController
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return service.getAll();
    }
    @PostMapping("/events/new")
    public void saveNewEvent(@RequestBody Event event) {
        service.save(event);
    }
    @PutMapping("/events/update/{id}")
    public void updateEvent(@PathVariable Integer id, @RequestBody Event event) {
        service.update(id, event);
    }
    @DeleteMapping("/events/delete/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        service.delete(id);
    }
    @GetMapping("/owner/{id}/events")
    public List<Event> getEventsOwner(@PathVariable Integer id) {
        return service.find(id);
    }
}
