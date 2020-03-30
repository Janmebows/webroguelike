package com.fdm.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "MAPS")
public class Map {
	// The map is defined such that 0,0 is the TOP LEFT CORNER
	public static final int XMAX = 50;
	public static final int YMAX = 20;

	// its will correspond to cell types for now - we may move this to a class
	// 1 . walls
	// 0 . passable
	int[][] map = new int[XMAX][YMAX];
	PlayerCharacter character = new PlayerCharacter("Janme", 10, 10);

	Map() {
		makeMap();

	}

	private void makeMap() {
		for (int j = 0; j < YMAX; ++j) {
			for (int i = 0; i < XMAX; ++i) {
				map[i][j] = j == 0 || j == YMAX - 1 || i == 0 || i == XMAX - 1 ? 1 : 0;
			}
		}
	}

	public int getCell(int x, int y) {
		return map[x][y];
	}

	public void printMap() {
		for (int j = 0; j < YMAX; ++j) {
			for (int i = 0; i < XMAX; ++i) {
				print(i, j);

			}
			System.out.println();
		}
	}

	public void print(int x, int y) {
		if (character.isAtPosition(x, y))
			System.out.print(MapDrawingConstants.PLAYER_SYMBOL);
		else {
			System.out.print(MapDrawingConstants.charFromValue(map[x][y]));
		}
	}
}
