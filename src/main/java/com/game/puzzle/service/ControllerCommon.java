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



public abstract class ControllerCommon<T extends ServiceCommon> {

    @Autowired
    T gameService;

    @RequestMapping(value = "/{level}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Game> getStoredGame(@PathVariable(value = "level") Integer level , HttpServletRequest request) {
        Game g = gameService.assignNewGame(level,request.getHeader("username"));
        return new ResponseEntity<>(g, HttpStatus.OK);
    }


	@RequestMapping(value = "/leaderboard/{level}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Game> getLeaderBoard(@PathVariable(value = "level") Integer level) {
		return gameService.getLeaderBoard(level);
	}

    @RequestMapping(value = "/solved", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response solved(@RequestBody Game game) {
        Response response = gameService.solved(game);
        return response;
    }
}