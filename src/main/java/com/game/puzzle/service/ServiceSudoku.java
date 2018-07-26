package com.game.puzzle.service;

import com.game.puzzle.entity.Game;
import com.game.puzzle.entity.Response;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ServiceSudoku extends ServiceCommon {


    @Override
    Game assignNewGame(Integer level, String username) {


        Game g = super.assignNewGame(9, username);
        return prepareGame(g);

    }

    private Game prepareGame(Game g) {
        int hide = 50;
        Random random = new Random();

        while(hide-->0)
        {
            boolean hidden =false;

            while(!hidden)
            {
                int index = random.nextInt(81);
                if(g.getGame().get(index)!=0)
                {
                    g.getGame().set(index,0);
                    hidden=true;
                }
            }
        }
        return g;
    }

    public void initNewGame(Game g) {
        int game[][] = new int[9][9];

        game = generateNewGame(game,0);
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                g.getGame().add(game[i][j]);
            }
        }
    }

    private int[][] generateNewGame(int[][] game, int index) {
        if( index >80)
        {
            return game;
        }

        int x = index % 9;
        int y = index / 9;

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++)
        {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        while (numbers.size() > 0) {
            int number = getValidNumber(game, x, y, numbers);
            if (number == -1)
                return null;

            game[y][x] = number;
            int[][] tmpGame = generateNewGame(game, index + 1);
            if (tmpGame != null)
                return tmpGame;
            game[y][x] = 0;
        }

        return null;


    }

    private int getValidNumber(int[][] game, int x, int y, ArrayList<Integer> numbers) {
        while (numbers.size() > 0) {
            int number = numbers.remove(0);
            if (check(game,y,x,number))
                return number;
        }
        return -1;
    }

    private int getSegment(int pos)
    {
        if(pos>=0  && pos < 3)
        {
            return 0;
        }
        else if(pos>=3  && pos < 6)
        {
            return 3;
        }
        else
        {
            return 6;
        }
    }

    private boolean check(int[][] game, int row, int col, int num)
    {

        int r =getSegment(row),c =getSegment(col);

        for(int i=r;i < r+3;i++)
        {
            for(int j=c;j<c+3;j++)
            {
                if(num==game[i][j])
                {
                    return false;
                }
            }
        }
        for(int i=0;i<9;i++)
        {
            if(game[i][col]==num || game[row][i] ==num)
            {
                return false;
            }
        }
        return true;
    }


    protected void updateOnSuccess(Game assignedGame, Game solvedGame) {
        if (assignedGame.getTimeTaken() == null || assignedGame.getTimeTaken() > solvedGame.getTimeTaken()) {
            assignedGame.setTimeTaken(solvedGame.getTimeTaken());
        }
        gameRepository.save(assignedGame);
    }


    protected Response validations(Long currentMillis, Game game, Game solvedGame) {
        String status = SUCCESS_STATUS;
        String message = null;

        return new Response(status, message);
    }


}
