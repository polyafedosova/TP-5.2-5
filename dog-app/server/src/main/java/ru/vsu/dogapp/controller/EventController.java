package ru.vsu.dogapp.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vsu.dogapp.dto.EventDto;
import ru.vsu.dogapp.service.EventService;

import javax.validation.Valid;
import java.time.format.DateTimeParseException;
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
        try {
            service.save(username, event);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date or time format", e);
        }
    }

    @PutMapping("/{event_id}/update")
    @ApiOperation("Updating information about an event")
    public void updateEvent(@PathVariable Integer event_id, @Valid @RequestBody EventDto event, @PathVariable String username) {
        try {
            service.update(event_id, event);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date or time format", e);
        }
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
