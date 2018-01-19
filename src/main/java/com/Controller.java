package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class Controller {

    @Autowired
    private Score score;

   @RequestMapping(value = "/{name}/{score}",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Score index(@PathVariable("name") String name ,@PathVariable("score") Integer num) {
        score.put(name,num);
        return score;
    }
}