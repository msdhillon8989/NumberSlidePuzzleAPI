package com.game.puzzle.service;

import com.game.puzzle.entity.Game;
import com.game.puzzle.entity.LeaderBoard;
import com.game.puzzle.entity.Response;
import com.game.puzzle.entity.UserScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
	private GameRepository gameRepository;

	@RequestMapping(value = "/new", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Game> getNewGame(@RequestBody Game game) {
		gameRepository.save(game);
		return new ResponseEntity<>(game, HttpStatus.OK);
	}




}