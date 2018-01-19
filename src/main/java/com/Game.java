package com;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class Game {
    private String token;
    private List<Integer> game;
    private Integer level;
    private List<Integer> solution;

    public Game(Integer num) {
        level = num;
        initNewGame();
    }

    private void initNewGame() {
        game = new ArrayList<>();
        for(int i =0;i<level*level-1;i++)
        {
            game.add(i+1);
        }
        Collections.shuffle(game);
        game.add(0);
        token = UUID.randomUUID().toString().replaceAll("-","");
    }

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public List<Integer> getGame() {
        return game;
    }

    public void setGame(List<Integer> game) {
        this.game = game;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Integer> getSolution() {
        return solution;
    }

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
    }
}
