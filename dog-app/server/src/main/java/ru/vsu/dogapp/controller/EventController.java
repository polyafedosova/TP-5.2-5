package ru.vsu.dogapp.controller;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("Saving information about a new event")
    public void saveNewEvent(@PathVariable String username, @Valid @RequestBody EventDto event) {
        service.save(username, event);
    }

    @PutMapping("/{event_id}/update")
    @ApiOperation("Updating information about an event")
    public void updateEvent(@PathVariable Integer event_id, @Valid @RequestBody EventDto event, @PathVariable String username) {
        service.update(event_id, event);
    }

    @DeleteMapping("/{event_id}/delete")
    @ApiOperation("Deleting information about an event")
    public void deleteEvent(@PathVariable Integer event_id, @PathVariable String username) {
        service.delete(event_id);
    }

    @GetMapping()
    @ApiOperation("Getting a list of all owner's events")
    public List<EventDto> getEvents(@PathVariable String username) {
        return service.getByOwner(username);
    }
}
