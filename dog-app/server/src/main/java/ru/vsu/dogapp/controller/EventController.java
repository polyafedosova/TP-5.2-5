package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.EventDto;
import ru.vsu.dogapp.service.EventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/owner/{username}/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping("/new")
    public void saveNewEvent(@PathVariable String username, @Valid @RequestBody EventDto event) {
        service.save(username, event);
    }
    @PutMapping("/{id}/update")
    public void updateEvent(@PathVariable Integer id, @Valid @RequestBody EventDto event, @PathVariable String username) {
        service.update(id, event);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteEvent(@PathVariable Integer id, @PathVariable String username) {
        service.delete(id);
    }
    @GetMapping()
    public List<EventDto> getEvents(@PathVariable String username) {
        return service.getByOwner(username);
    }
}
