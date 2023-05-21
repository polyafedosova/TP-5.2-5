package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.OwnerDto;
import ru.vsu.dogapp.service.OwnerService;

import javax.validation.Valid;

@RestController
public class OwnerController {

    private final OwnerService service;

    public OwnerController(OwnerService service) {
        this.service = service;
    }

    @PostMapping("/owner/{username}")
    public OwnerDto get(@PathVariable String username) {
        return service.find(username);
    }
    @PostMapping("/registration")
    public void saveNewOwner(@Valid  @RequestBody OwnerDto owner) {
        service.save(owner);
    }
    @PutMapping("/owner/{username}/update")
    public void updateOwner(@PathVariable String username, @Valid @RequestBody OwnerDto owner) {
        service.update(username, owner);
    }
    @PutMapping("/owner/{username}/update/password")
    public void updatePassword(@PathVariable String username, @RequestBody String oldPassword, @RequestBody String newPassword) {
        service.updatePassword(username, oldPassword, newPassword);
    }
    @DeleteMapping("/owner/{username}/delete")
    public void deleteOwner(@PathVariable String username) {
        service.delete(username);
    }
}
