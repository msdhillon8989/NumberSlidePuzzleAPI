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

    Game assignNewGame(Integer level,String username) {
        if(level <=3)
        {
            level =3;
        }
        else if(level >5)
        {
            level =5;
        }
        if (username != null) {
            Game g = gameRepository.findByUsernameAndLevel(username,level);
            if (g != null) {
                g.initNewGame();

            } else {
                g = new Game(level);
                g.setUsername(username);

            }
            g.setGameAssignedTime(System.currentTimeMillis());
            gameRepository.save(g);
            return g;
        }
        return null;
    }

    List<Game> getLeaderBoard(Integer level) {
        return gameRepository.findGameByLevelAndTimeTakenNotNullOrderByTimeTakenAscMovesAsc(level);
    }

    Response solved(Game solvedGame) {
        Long currentMillis = System.currentTimeMillis();
        try {
            if (solvedGame != null && solvedGame.getUsername() != null) {
                Game assignedGame = gameRepository.findByUsernameAndLevel(solvedGame.getUsername(),solvedGame.getLevel());


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
        int zp = 0;

        int MAX_ELEMENTS = game.getLevel()*game.getLevel();
        Integer array[] = new Integer[MAX_ELEMENTS];
        List<Integer> gameList = game.getGame();
        for (int i = 0; i < MAX_ELEMENTS; i++) {
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

        for (int i = 0; i < MAX_ELEMENTS-1; i++) {
            if (array[i] != (i + 1)) {
                return false;
            }
        }
        return true;
    }
}
