package com.game.puzzle.service;


import com.game.puzzle.entity.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {
    Game findByUsernameAndLevel(String username, Integer level);

    List<Game> findGameByLevelAndTimeTakenNotNullOrderByTimeTakenAscMovesAsc(Integer level);
}