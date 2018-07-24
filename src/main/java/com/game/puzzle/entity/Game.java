package com.game.puzzle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.game.puzzle.config.StringListConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Table(name = "game")
public class Game {

    @Column(name = "game")
    @Convert(converter = StringListConverter.class)
    public List<Integer> game;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "level")
    private Integer level;
    @Transient
    private List<Integer> solution;
    @Column(name = "time")
    private Long timeTaken;
    @Column(name = "moves")
    private Integer moves;
    @Column(name = "assigned_time")
    @JsonIgnore
    private Long gameAssignedTime;

    public Game() {

    }

    public Game(Integer num) {
        level = num;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getGameAssignedTime() {
        return gameAssignedTime;
    }

    public void setGameAssignedTime(Long gameAssignedTime) {
        this.gameAssignedTime = gameAssignedTime;
    }

    public Long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getGame() {
        return game;
    }

    public void setGame(List<Integer> game) {
        this.game = game;
    }

    public List<Integer> getSolution() {
        return solution;
    }

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
    }

    public Integer getMoves() {
        return moves;
    }

    public void setMoves(Integer moves) {
        this.moves = moves;
    }
}
