package com.game.puzzle.service;

import com.game.puzzle.entity.Game;
import com.game.puzzle.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameService {

    private static final String FAILED_STATUS = "FAILURE";
    @Autowired
    private GameRepository gameRepository;

    Game assignNewGame(String username) {
        if (username != null) {
            Game g = gameRepository.findByUsername(username);
            if (g != null) {
                g.initNewGame();

            } else {
                g = new Game(3);
                g.setUsername(username);

            }
            g.setGameAssignedTime(System.currentTimeMillis());
            gameRepository.save(g);
            return g;
        }
        return null;
    }

    List<Game> getLeaderBoard() {
        return gameRepository.findGameByTimeTakenNotNullOrderByTimeTakenAscMovesAsc();
    }

    Response solved(Game solvedGame) {
        Long currentMillis = System.currentTimeMillis();
        try {
            if (solvedGame != null && solvedGame.getUsername() != null) {
                Game assignedGame = gameRepository.findByUsername(solvedGame.getUsername());


                Response response = validations(currentMillis, assignedGame, solvedGame);

                if (response.getStatus().equals("SUCCESS")) {
                   
                    if (assignedGame.getTimeTaken() == null) {
                        assignedGame.setTimeTaken(solvedGame.getTimeTaken());
                        assignedGame.setMoves(solvedGame.getSolution().size());

                    } else if (assignedGame.getTimeTaken() > solvedGame.getTimeTaken() || (assignedGame.getTimeTaken() == solvedGame.getTimeTaken() && assignedGame.getMoves() > solvedGame.getSolution().size())) {
                        assignedGame.setMoves(assignedGame.getSolution().size());
                        assignedGame.setTimeTaken(solvedGame.getTimeTaken());
                    }
                    gameRepository.save(assignedGame);
                }
                return response;
            } else {
                return new Response(FAILED_STATUS, "Username is empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(FAILED_STATUS, "Exception Something went wrong " + e.getMessage());
        }

    }


    private Response validations(Long currentMillis, Game game, Game solvedGame) {
        String status = "SUCCESS";
        String message = null;

        if (game == null) {
            status = FAILED_STATUS;
            message = "Game was not assigned";
        } else {
            game.setSolution(solvedGame.getSolution());
            if (game.getSolution() == null || !isValidSolution(game)) {
                status = FAILED_STATUS;
                message = "Invalid solution";
            }

            Long timeTaken = currentMillis - game.getGameAssignedTime();
            timeTaken = timeTaken / 100;

            if (timeTaken - solvedGame.getTimeTaken() > 100) {
                status = FAILED_STATUS;
                message = "Are u trying to cheat ??";
            }
        }

        return new Response(status, message);
    }

    private boolean isValidSolution(Game game) {
        int zp = 8;

        Integer array[] = new Integer[9];
        List<Integer> gameList = game.getGame();
        for (int i = 0; i < 9; i++) {
            array[i] = gameList.get(i);
            if (array[i] == 0) {
                zp = i;
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
