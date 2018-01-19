package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/game")
@RestController
public class Controller2 {

    @Autowired
    private Score score;

    @RequestMapping(value = "/{name}/{score}",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Score index(@PathVariable("name") String name , @PathVariable("score") Integer num) {
        score.put(name,num);
        return score;
    }

    @RequestMapping(value = "/new/{level}",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Game> getNewGame( @PathVariable("level") Integer num)
    {
        Game g = new Game(num);

        ResponseEntity<Game> responseEntity= new ResponseEntity<Game>(g, HttpStatus.OK);
        return responseEntity;
    }
}