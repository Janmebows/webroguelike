package com.fdm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fdm.model.Actor;
import com.fdm.model.Direction;
import com.fdm.model.Enemy;
import com.fdm.model.Map;
import com.fdm.model.MapDrawingConstants;
import com.fdm.model.PlayerCharacter;

import PlaceholderTesting.IView;

public class PlayerMovementController {
	
	Map map;
	PlayerCharacter playerCharacter;
	List<Actor> enemyList;
	private IView iView;
	boolean isRunning = true;
	
	
	public PlayerMovementController(Map map, PlayerCharacter playerCharacter, IView iView) {
		super();
		this.map = map;
		this.playerCharacter = playerCharacter;
		this.iView = iView;
		this.enemyList = new ArrayList<Actor>();
		Actor enemy = new Enemy("hi", 2, 4);
		enemyList.add(enemy);
	}

	public void handle() {
		
		printMap();
		while (isRunning) {
			
			String input = iView.getUserInput();
			if(input.contentEquals("Q")) {
				System.out.println("Quitting");
				isRunning = false;
			} else {
				Direction inputDirection = processInput(input);
				boolean result = playerCharacter.moveChar(inputDirection);
				if(result) {
					printMap();
				}
			}			
		}
	}
	
	public Direction processInput(String input) {
		//currently using wasd.
		switch (input) {
		case "w":
			return Direction.UP;
		case "s":
			return Direction.DOWN;
		case "a":
			return Direction.LEFT;
		case "d":
			return Direction.RIGHT;
		}
		return Direction.NONE;
	}	
	
	public void printMap() {
		for (int j = 0; j < map.YMAX; ++j) {
			for (int i = 0; i < map.XMAX; ++i) {
				print(i, j);
			}
			System.out.println();
		}
	}

	public void print(int x, int y) {
		boolean enemyNotAtPosition = enemyList.stream().filter(enemy -> enemy.isAtPosition(x, y)).collect(Collectors.toList()).isEmpty();
		if (playerCharacter.isAtPosition(x, y)) {
			System.out.print(MapDrawingConstants.PLAYER_SYMBOL);
		} else if (!enemyNotAtPosition){
			System.out.print(MapDrawingConstants.ENEMY_SYMBOL);
		} else {
			System.out.print(MapDrawingConstants.charFromValue(map.get(x,y)));
		}
	}
}
