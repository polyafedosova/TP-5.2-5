package ru.vsu.dogapp.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vsu.dogapp.dto.OwnerDto;
import ru.vsu.dogapp.dto.jwt.JwtRequest;
import ru.vsu.dogapp.dto.jwt.JwtResponse;
import ru.vsu.dogapp.service.OwnerService;
import ru.vsu.dogapp.service.auth.AuthService;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Validated
public class OwnerController {

    private final OwnerService service;
    private final AuthService authService;

    @PostMapping("/owner/{username}")
    @ApiOperation("Getting information about an owner")
    public OwnerDto get(@PathVariable String username) {
        return service.find(username);
    }

    @PostMapping("api/auth/registration")
    @ApiOperation("Registration new owner")
    public ResponseEntity<String> saveNewOwner(@Valid @RequestBody OwnerDto owner) {
        try {
            boolean saved = service.save(owner);
            if (!saved) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Login already exists");
            } else {
                return ResponseEntity.ok("Registration is successful");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred");
        }
    }

    @PostMapping("api/auth/login")
    @ApiOperation("User authentication and JWT token generation")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PutMapping("/owner/{username}/update")
    @ApiOperation("Updating information about an owner")
    public ResponseEntity<String> updateOwner(@PathVariable String username, @Valid @RequestBody OwnerDto owner) {
        try {
            service.update(username, owner);
            return ResponseEntity.ok("Owner updated successfully");
        } catch (ResponseStatusException e) {
            HttpStatus status = e.getStatus();
            String message = e.getReason();
            return ResponseEntity.status(status).body(message);
        }
    }

    @PutMapping("/owner/{username}/update/password")
    @ApiOperation("Updating an owner`s password")
    public void updatePassword(@PathVariable String username, String newPassword) {
        service.updatePassword(username, newPassword);
    }
    @DeleteMapping("/owner/{username}/delete")
    @ApiOperation("Deleting information about an owner")
    public void deleteOwner(@PathVariable String username) {
        service.delete(username);
    }
}
