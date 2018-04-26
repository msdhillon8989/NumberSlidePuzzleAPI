package com;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
		Random random = new Random();
		int arr[][] = new int[level][level];
		int x=1;
		for(int i=0;i<level;i++)
		{
			for(int j=0;j<level;j++)
			{
				arr[i][j] = x++;
			}
		}

		arr[level-1][level-1] =0;

		int posx=level-1;
		int posy=level-1;

		int posChange[][] = {{0,-1},{0,1},{1,0},{-1,0}};

		for(int i =0;i<200;i++)
		{
			int newx=posx,newy=posy;
			boolean canSlide =false;
			while(true)
			{
				int shift =  random.nextInt(4);
				int shiftTo[] = posChange[shift];

				newx = posx+shiftTo[0];
				newy = posy+shiftTo[1];

				if(newx>=0 && newx<level && newy>=0 && newy<level)
				{
					break;
				}
			}

			arr[posx][posy] = arr[newx][newy];
			arr[newx][newy] =0;
			posx = newx;
			posy = newy;
		}

		game = new ArrayList<>();
		for(int i=0;i<level;i++)
		{
			for(int j=0;j<level;j++)
			{
				game.add(arr[i][j]);
			}
		}




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
