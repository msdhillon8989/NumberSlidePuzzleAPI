package com.game.puzzle.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HealthCheckController {

    @GetMapping({"/", "/home", "/status"})
    public String getStatus() {
        return "Application is up and running";
    }
}
