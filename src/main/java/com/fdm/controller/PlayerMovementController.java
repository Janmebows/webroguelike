package com.fdm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		Actor enemy2 = new Enemy("bye", 5, 8);
		Actor enemy3 = new Enemy("running out of things", 2, 4);
		enemyList.add(enemy);
		enemyList.add(enemy2);
		enemyList.add(enemy3);
	}

	public void handle() {

		printMap();
		while (isRunning) {

			String input = iView.getUserInput();
			if (input.contentEquals("Q")) {
				System.out.println("Quitting");
				isRunning = false;
			} else {
				if (input.equals("w") || input.equals("s") || input.equals("a") || input.equals("d")) {
					Direction inputDirection = processInput(input);
					boolean result = moveChar(playerCharacter, inputDirection);
					if (result) {
						printMap();
					}
				}
			}
		}
	}

	public boolean moveChar(PlayerCharacter playerChar, Direction direction) {

		int x = playerChar.getX();
		int y = playerChar.getY();
		switch (direction) {
		case DOWN:
			if (map.get(x, y + 1) == 0) {
				List<Actor> enemiesAtPosition = enemyList.stream()
						.filter(enemy -> enemy.isAtPosition(x, y + 1) && enemy.isAlive()).collect(Collectors.toList());
				if (!enemiesAtPosition.isEmpty()) {
					playerChar.attack((Enemy) enemiesAtPosition.get(0));
				} else {
					playerChar.UpdatePosition(x, y + 1);
				}
				return true;
			}
			break;
		case UP:
			if (map.get(x, y - 1) == 0) {
				List<Actor> enemiesAtPosition = enemyList.stream()
						.filter(enemy -> enemy.isAtPosition(x, y - 1) && enemy.isAlive()).collect(Collectors.toList());
				if (!enemiesAtPosition.isEmpty()) {
					playerChar.attack((Enemy) enemiesAtPosition.get(0));
				} else {
					playerChar.UpdatePosition(x, y - 1);
				}
				return true;
			}
			break;
		case LEFT:
			if (map.get(x - 1, y) == 0) {
				List<Actor> enemiesAtPosition = enemyList.stream()
						.filter(enemy -> enemy.isAtPosition(x - 1, y) && enemy.isAlive()).collect(Collectors.toList());
				if (!enemiesAtPosition.isEmpty()) {
					playerChar.attack((Enemy) enemiesAtPosition.get(0));
				} else {
					playerChar.UpdatePosition(x - 1, y);
				}
				return true;
			}
			break;
		case RIGHT:
			if (map.get(x + 1, y) == 0) {
				List<Actor> enemiesAtPosition = enemyList.stream()
						.filter(enemy -> enemy.isAtPosition(x + 1, y) && enemy.isAlive()).collect(Collectors.toList());
				if (!enemiesAtPosition.isEmpty()) {
					playerChar.attack((Enemy) enemiesAtPosition.get(0));
				} else {
					playerChar.UpdatePosition(x + 1, y);
				}
				return true;
			}
			break;
		}
		return false;
	}

//	private boolean attack(PlayerCharacter playerCharacter, Direction inputDirection) {
//		
//		int xPos = playerCharacter.getX();
//		int yPos = playerCharacter.getY();
//		
//		System.out.println("x=" + xPos);
//		System.out.println("y=" + yPos);
//		
//		Enemy target;
//		
//		switch (inputDirection) {
//		case UP:
//			target = (Enemy) enemyList.stream().filter(enemy -> enemy.isAtPosition(xPos, yPos - 1) && enemy.isAlive()).collect(Collectors.toList()).get(0);
//			playerCharacter.attack(target);
//			return(true);
//		case DOWN:
//			target = (Enemy) enemyList.stream().filter(enemy -> enemy.isAtPosition(xPos, yPos + 1) && enemy.isAlive()).collect(Collectors.toList()).get(0);
//			playerCharacter.attack(target);
//			return(true);
//		case LEFT:
//			target = (Enemy) enemyList.stream().filter(enemy -> enemy.isAtPosition(xPos - 1, yPos) && enemy.isAlive()).collect(Collectors.toList()).get(0);
//			playerCharacter.attack(target);
//			return(true);
//		case RIGHT:
//			target = (Enemy) enemyList.stream().filter(enemy -> enemy.isAtPosition(xPos + 1, yPos) && enemy.isAlive()).collect(Collectors.toList()).get(0);
//			playerCharacter.attack(target);
//			return(true);
//		}
//		return false;
//	}

	public Direction processInput(String input) {
		// currently using wasd.
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
		boolean enemyAtPosition = enemyList.stream().anyMatch(enemy -> enemy.isAtPosition(x, y) && enemy.isAlive());
		if (playerCharacter.isAtPosition(x, y)) {
			System.out.print(MapDrawingConstants.PLAYER_SYMBOL);
		} else if (enemyAtPosition) {
			System.out.print(MapDrawingConstants.ENEMY_SYMBOL);
		} else {
			System.out.print(MapDrawingConstants.charFromValue(map.get(x, y)));
		}
	}
}
