package com.game.puzzle.service;

import com.game.puzzle.entity.Game;
import com.game.puzzle.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class GameService {

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

    abstract Response solved(Game solvedGame);

}
