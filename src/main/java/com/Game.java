package com;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Game {


	private String username;
	private List<Integer> game;
	private Integer level;
	private List<Integer> solution;
	private Long seconds;
	private Long gameAssignedTime;

	public Game() {

	}

	public Game(Integer num) {
		level = num;
		initNewGame();
	}

	public Long getGameAssignedTime() {
		return gameAssignedTime;
	}

	public void setGameAssignedTime(Long gameAssignedTime) {
		this.gameAssignedTime = gameAssignedTime;
	}

	public Long getSeconds() {
		return seconds;
	}

	public void setSeconds(Long seconds) {
		this.seconds = seconds;
	}

	private void initNewGame() {
		game = new ArrayList<>();
		for (int i = 0; i < level * level - 1; i++) {
			game.add(i + 1);
		}
		Collections.shuffle(game);
		game.add(0);

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

	public String getGameString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (Integer num : game) {
			stringBuilder.append(num);
		}
		return stringBuilder.toString();
	}
}
