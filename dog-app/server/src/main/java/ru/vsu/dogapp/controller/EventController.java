package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.EventDto;
import ru.vsu.dogapp.entity.Event;
import ru.vsu.dogapp.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/owner/{owner_id}/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping("/new")
    public void saveNewEvent(@PathVariable Integer owner_id, @RequestBody EventDto event) {
        service.save(owner_id, event);
    }
    @PutMapping("/{id}/update")
    public void updateEvent(@PathVariable Integer id, @RequestBody EventDto event) {
        service.update(id, event);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteEvent(@PathVariable Integer id) {
        service.delete(id);
    }
    @GetMapping()
    public List<EventDto> getEventsOwner(@PathVariable Integer owner_id) {
        return service.find(owner_id);
    }
}
