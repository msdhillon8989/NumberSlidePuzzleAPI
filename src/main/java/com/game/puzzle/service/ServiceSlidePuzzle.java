package com.game.puzzle.service;

import com.game.puzzle.entity.Game;
import com.game.puzzle.entity.Response;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class ServiceSlidePuzzle extends ServiceCommon {


    Game assignNewGame(Integer level, String username) {
        if (level <= 3) {
            level = 3;
        } else if (level > 5) {
            level = 5;
        }
        return super.assignNewGame(level, username);
    }

    public void initNewGame(Game g) {
        int level = g.getLevel();
        Random random = new Random();
        int arr[][] = new int[level][level];
        int x = 1;
        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                arr[i][j] = x++;
            }
        }

        arr[level - 1][level - 1] = 0;

        int posx = level - 1;
        int posy = level - 1;

        int posChange[][] = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};

        int lastx = posx;
        int lasty = posy;
        for (int i = 0; i < 50 * level; i++) {
            int newX, newY ;

            while (true) {
                int shift = random.nextInt(4);
                int shiftTo[] = posChange[shift];

                newX = posx + shiftTo[0];
                newY = posy + shiftTo[1];

                if (newX >= 0 && newX < level && newY >= 0 && newY < level) {
                    if (!(lastx == newX && lasty == newY)) {
                        lastx = posx;
                        lasty = posy;
                        break;
                    }
                }
            }

            arr[posx][posy] = arr[newX][newY];
            arr[newX][newY] = 0;
            posx = newX;
            posy = newY;
        }
        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                g.getGame().add(arr[i][j]);
            }
        }
    }




    protected void updateOnSuccess(Game assignedGame, Game solvedGame) {
        if (assignedGame.getTimeTaken() == null) {
            assignedGame.setTimeTaken(solvedGame.getTimeTaken());
            assignedGame.setMoves(solvedGame.getSolution().size());

        } else if (assignedGame.getTimeTaken() > solvedGame.getTimeTaken() || (assignedGame.getTimeTaken() == solvedGame.getTimeTaken() && assignedGame.getMoves() > solvedGame.getSolution().size())) {
            assignedGame.setMoves(assignedGame.getSolution().size());
            assignedGame.setTimeTaken(solvedGame.getTimeTaken());
        }
        gameRepository.save(assignedGame);
    }


    protected Response validations(Long currentMillis, Game game, Game solvedGame) {
        String status = SUCCESS_STATUS;
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

        int MAX_ELEMENTS = game.getLevel() * game.getLevel();
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

        for (int i = 0; i < MAX_ELEMENTS - 1; i++) {
            if (array[i] != (i + 1)) {
                return false;
            }
        }
        return true;
    }
}
