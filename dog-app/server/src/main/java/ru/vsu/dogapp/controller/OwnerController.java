package ru.vsu.dogapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.dto.OwnerDto;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.service.OwnerService;

import javax.validation.Valid;
import java.util.List;

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
    @PutMapping("/owner/{owner_id}/update")
    public void updateOwner(@PathVariable Integer owner_id, @Valid @RequestBody OwnerDto owner) {
        service.update(owner_id, owner);
    }
    @PutMapping("/owner/{owner_id}/update/password")
    public void updateOwner(@PathVariable Integer owner_id, @RequestBody String oldPassword, @RequestBody String newPassword) {
        service.updatePassword(owner_id, oldPassword, newPassword);
    }
    @DeleteMapping("/owner/{owner_id}/delete")
    public void deleteOwner(@PathVariable Integer owner_id) {
        service.delete(owner_id);
    }
}
