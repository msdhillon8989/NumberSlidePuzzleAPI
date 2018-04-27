package com.game.puzzle.service;

import com.game.puzzle.entity.Game;
import com.game.puzzle.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/game")
@RestController
public class GameController {


    //private static HashMap<String, Game> assignedGames = new HashMap<>();



    @Autowired
    private GameService gameService;

	/*@RequestMapping(value = "/new", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Game> getNewGame(HttpServletRequest request) {
		Game g = new Game(3);
		g.setUsername(request.getHeader("username"));
		g.setGameAssignedTime(System.currentTimeMillis());
		assignedGames.put(g.getUsername(), g);
		ResponseEntity<Game> responseEntity = new ResponseEntity<>(g, HttpStatus.OK);
		return responseEntity;
	}*/

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Game> getStoredGame(HttpServletRequest request) {
        Game g = gameService.assignNewGame(request.getHeader("username"));

        ResponseEntity<Game> responseEntity = new ResponseEntity<>(g, HttpStatus.OK);
        return responseEntity;
    }


	@RequestMapping(value = "/leaderboard", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Game> getLeaderBoard() {
		return gameService.getLeaderBoard();
	}

	/*@RequestMapping(value = "/assigned", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Game> getAssigned() {
		return assignedGames;
	}*/


    @RequestMapping(value = "/solved", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response solved(@RequestBody Game game) {


        Response response = gameService.solved(game);
        return response;

    }


}