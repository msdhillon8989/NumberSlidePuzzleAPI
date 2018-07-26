package com.game.puzzle.service;

import com.game.puzzle.entity.Game;
import com.game.puzzle.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class ServiceCommon {

    static final String FAILED_STATUS = "FAILURE";
    static final String SUCCESS_STATUS = "SUCCESS";


    @Autowired
    protected GameRepository gameRepository;

    Game assignNewGame(Integer level,String username) {
        if (username != null) {
            Game g = gameRepository.findByUsernameAndLevel(username,level);
            if (g == null) {
                g = new Game(level);
                g.setUsername(username);
            }
            g.setGame(new ArrayList<>());
            initNewGame(g);
            g.setGameAssignedTime(System.currentTimeMillis());
            gameRepository.save(g);
            return g;
        }
        return null;
    }

    abstract void initNewGame(Game g);

    List<Game> getLeaderBoard(Integer level) {
        return gameRepository.findGameByLevelAndTimeTakenNotNullOrderByTimeTakenAscMovesAsc(level);
    }

    final Response solved(Game solvedGame) {
        Long currentMillis = System.currentTimeMillis();
        try {
            if (solvedGame != null && solvedGame.getUsername() != null) {
                Game assignedGame = gameRepository.findByUsernameAndLevel(solvedGame.getUsername(), solvedGame.getLevel());
                Response response = validations(currentMillis, assignedGame, solvedGame);
                if (response.getStatus().equals(SUCCESS_STATUS)) {
                    updateOnSuccess(assignedGame,solvedGame);
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

    protected abstract void updateOnSuccess(Game assignedGame, Game solvedGame);

    protected abstract Response validations(Long currentMillis, Game assignedGame, Game solvedGame);

}
