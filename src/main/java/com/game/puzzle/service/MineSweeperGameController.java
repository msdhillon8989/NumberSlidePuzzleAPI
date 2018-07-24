package com.game.puzzle.service;


import com.game.puzzle.entity.Game;
import com.game.puzzle.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/mines")
@RestController
public class MineSweeperGameController {

    @Autowired
    MineSweeperGameService mineSweeperGameService;

    @RequestMapping(value = "/{level}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Game> getStoredGame(@PathVariable(value = "level") Integer level, HttpServletRequest request) {

        Game game = mineSweeperGameService.assignNewGame(level, request.getHeader("username"));
        game.setMoves(0);
        ResponseEntity<Game> responseEntity = new ResponseEntity<>(game, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/leaderboard/{level}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Game> getLeaderBoard(@PathVariable(value = "level") Integer level) {
        return mineSweeperGameService.getLeaderBoard(level);
    }

    @RequestMapping(value = "/solved", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response solved(@RequestBody Game game) {
        Response response = mineSweeperGameService.solved(game);
        return response;
    }
}
