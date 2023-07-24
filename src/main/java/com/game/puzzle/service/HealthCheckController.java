package com.game.puzzle.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api")
@RestController
public class HealthCheckController {

    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getStoredGame() {

        return new ResponseEntity<>("{\"status\" : \"UP\"}", HttpStatus.OK);
    }
}
