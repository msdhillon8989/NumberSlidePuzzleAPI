package com.game.puzzle.service;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthCheckController {

    @GetMapping({"/", "/home", "/status"})
    public String getStatus() {
        return "Application is up and running";
    }

}
