package com.game.puzzle.service;

import com.game.puzzle.entity.Game;
import com.game.puzzle.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MineSweeperGameService {

    private static final String FAILED_STATUS = "FAILURE";
    @Autowired
    private GameRepository gameRepository;

    Game assignNewGame(Integer level, String username) {

        if (username != null) {
            Game g = gameRepository.findByUsernameAndLevel(username, level);
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

    private Set<String> placeMines(int size, int totalMines) {
        Set<String> minesPositionSet = new HashSet<>();

        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < totalMines) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            if (minesPositionSet.add(x + "#" + y)) {
                minesPlaced++;
            }
        }
        return minesPositionSet;
    }


    public void initNewGame(Game g) {
        int size = 20;
        g.setGame(new ArrayList<>());

        int arr[][] = getMinesArea(size, g.getLevel());

        int i = 0;
        int sum = 0;
        for (int row[] : arr) {
            for (int col : row) {
                g.getGame().add(col);
                if (col < 0) {
                    sum += i;
                }
                i++;
            }
        }
        g.setMoves(sum);
        System.out.println("Total level sum " + sum);

    }

    private int[][] getMinesArea(int size, int totalMines) {
        Set<String> minesPositions = placeMines(size, totalMines);
        int arr[][] = new int[size][size];
        int neighbours[][] = {
                {-1, -1}, {0, -1}, {1, -1},
                {-1, 0}, {1, 0},
                {-1, 1}, {0, 1}, {1, 1}
        };
        for (String minePosition : minesPositions) {
            int x = Integer.valueOf(minePosition.substring(0, minePosition.indexOf("#")));
            int y = Integer.valueOf(minePosition.substring(minePosition.indexOf("#") + 1));
            arr[x][y] = -9;
            for (int neighbour[] : neighbours) {
                int newx = x + neighbour[0];
                int newy = y + neighbour[1];
                if (newx >= 0 && newx < size && newy >= 0 && newy < size) {
                    arr[newx][newy]++;
                }
            }
        }
        return arr;
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

                    if (assignedGame.getTimeTaken() == null  || assignedGame.getTimeTaken()>solvedGame.getTimeTaken()) {
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

            if (solvedGame.getMoves() == null || (!game.getMoves().equals(solvedGame.getMoves()))) {
                status = FAILED_STATUS;
                message = "Invalid solution";
            }

            Long timeTaken = currentMillis - game.getGameAssignedTime();
            timeTaken = timeTaken / 100;
            System.out.println("TimeTaken :" +timeTaken);
            if (timeTaken - solvedGame.getTimeTaken() > 400) {
                status = FAILED_STATUS;
                message = "Are u trying to cheat ??";
            }
        }

        return new Response(status, message);
    }
}
