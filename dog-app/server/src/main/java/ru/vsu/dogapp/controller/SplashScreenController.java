package ru.vsu.dogapp.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vsu.dogapp.entity.SplashScreen;
import ru.vsu.dogapp.service.OwnerService;
import ru.vsu.dogapp.service.SplashScreenService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/splashscreen")
public class SplashScreenController {

    private SplashScreenService service;
    private OwnerService ownerService;

    @GetMapping()
    @ApiOperation("Getting a list of all splash screen")
    public List<SplashScreen> getAll() {
        return service.getAll();
    }

    @PostMapping("/new")
    @ApiOperation("Saving information about a new splash screen")
    public void saveNew(@Valid @RequestBody SplashScreen splashScreen) {
        service.save(splashScreen);
    }

    @PutMapping("/{splashscreen_id}/update")
    @ApiOperation("Updating information about a splash screen")
    public void update(@PathVariable Integer splashscreen_id, @Valid @RequestBody SplashScreen splashScreen) {
        service.update(splashscreen_id, splashScreen);
    }

    @DeleteMapping("/{splashscreen_id}/delete")
    @ApiOperation("Deleting information about a splash screen")
    public void delete(@PathVariable Integer splashscreen_id) {
        service.delete(splashscreen_id);
    }

    @DeleteMapping("/deleteAll")
    @ApiOperation("Deleting all splash screens")
    public void deleteAll() {
        service.deleteAll();
    }

    @PostMapping("/toShow")
    @ApiOperation("Making splash screens visible to everyone users")
    public void toShow() {
        ownerService.makeShowTrue();
    }

    @PostMapping("/stopShow")
    @ApiOperation("Stopping showing splash screens to the user")
    public void dontShow(String username) {
        ownerService.makeShowFalse(username);
    }
}