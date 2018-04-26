package com;

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

@RequestMapping("/game")
@RestController
public class GameController {

	private static final String FAILED_STATUS = "FAILURE";

	private static HashMap<String, Game> assignedGames = new HashMap<>();

	@Autowired
	private LeaderBoard leaderBoard;

	@RequestMapping(value = "/new", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Game> getNewGame(HttpServletRequest request) {
		Game g = new Game(3);
		g.setUsername(request.getHeader("username"));
		g.setGameAssignedTime(System.currentTimeMillis());
		assignedGames.put(g.getUsername(), g);
		ResponseEntity<Game> responseEntity = new ResponseEntity<>(g, HttpStatus.OK);
		return responseEntity;
	}



	@RequestMapping(value = "/leaderboard", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public LeaderBoard getLeaderBoard() {
		return leaderBoard;
	}

	@RequestMapping(value = "/assigned", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Game> getAssigned() {
		return assignedGames;
	}


	@RequestMapping(value = "/solved", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Response solved(@RequestBody Game game) {
		Long currentMillis = System.currentTimeMillis();
		Response response = validations(game);
		if (response.getStatus().equals("SUCCESS")) {
			UserScore score = leaderBoard.get(game.getUsername());
			if (score == null) {
				score = new UserScore();
				score.setTime(game.getSeconds());
				score.setMoves(game.getSolution().size());
				leaderBoard.put(game.getUsername(), score);
			} else if (score.getTime() > game.getSeconds() || (score.getTime() == game.getSeconds() && score.getMoves() > game.getSolution().size())) {
				Game assignedGame = assignedGames.get(game.getUsername());
				Long timeTaken = currentMillis - assignedGame.getGameAssignedTime();
				timeTaken = timeTaken/100;

				if(timeTaken - game.getSeconds() > 10)
				{
					response.setStatus(FAILED_STATUS);
					response.setMessage("Are u trying to cheat ??");
					return  response;
				}
				score.setMoves(game.getSolution().size());
				score.setTime(game.getSeconds());
			}
		}
		return response;
	}

	private Response validations(Game game) {
		String status = "SUCCESS";
		String message = null;

		if (game.getUsername() == null) {
			status = FAILED_STATUS;
			message = "Username not present";
		} else if (assignedGames.get(game.getUsername()) == null) {
			status = FAILED_STATUS;
			message = "Game was not assigned";
		} else if (game.getGame() == null || game.getGameString().trim().equals("")) {
			status = FAILED_STATUS;
			message = "Game passed was empty";
		} else if (!game.getGameString().equals(assignedGames.get(game.getUsername()).getGameString())) {
			status = FAILED_STATUS;
			message = "Game mismatch";
		} else if (!isValidSolution(game)) {
			status = FAILED_STATUS;
			message = "Invalid solution";
		}

		return new Response(status, message);
	}

	private boolean isValidSolution(Game game) {
		int zp = 8;

		Integer array[] = new Integer[9];
		for (int i = 0; i < 9; i++) {
			array[i] = game.getGame().get(i);
			if(array[i] ==0)
			{
				zp =i;
			}
		}
		for (int clickPos : game.getSolution()) {
			try {
				array[zp] = array[clickPos];
				zp = clickPos;
				array[zp] = 0;

			} catch (Exception e) {
				return false;
			}
		}

		for (int i = 0; i < 8; i++) {
			if (array[i] != (i + 1)) {
				return false;
			}
		}

		return true;

	}
}