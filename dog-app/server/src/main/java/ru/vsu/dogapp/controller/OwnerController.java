package ru.vsu.dogapp.controller;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("Getting information about an owner")
    public OwnerDto get(@PathVariable String username) {
        return service.find(username);
    }
    @PostMapping("/registration")
    @ApiOperation("Registration")
    public void saveNewOwner(@Valid @RequestBody OwnerDto owner) {
        service.save(owner);
    }
    @PutMapping("/owner/{username}/update")
    @ApiOperation("Updating information about an owner")
    public void updateOwner(@PathVariable String username, @Valid @RequestBody OwnerDto owner) {
        service.update(username, owner);
    }
    @PutMapping("/owner/{username}/update/password")
    @ApiOperation("Updating an owner`s password")
    public void updatePassword(@PathVariable String username, String oldPassword, String newPassword) {
        service.updatePassword(username, oldPassword, newPassword);
    }
    @DeleteMapping("/owner/{username}/delete")
    @ApiOperation("Deleting information about an owner")
    public void deleteOwner(@PathVariable String username) {
        service.delete(username);
    }
}
