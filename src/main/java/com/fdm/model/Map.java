package com.fdm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "MAPS")
public class Map {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	// The map is defined such that 0,0 is the TOP LEFT CORNER
	public static final int XMAX = 20;
	public static final int YMAX = 10;

	// its will correspond to cell types for now - we may move this to a class
	// 1 . walls
	// 0 . passable
	public int[][] map = new int[XMAX][YMAX];
	//PlayerCharacter character = new PlayerCharacter("Janme", 6, 2, new Account());

	public Map() {
		makeMap();

	}

	private void makeMap() {
		for (int j = 0; j < YMAX; ++j) {
			for (int i = 0; i < XMAX; ++i) {
				map[i][j] = j == 0 || j == YMAX - 1 || i == 0 || i == XMAX - 1 ? 1 : 0;
			}
		}
	}

	public int get(int x, int y) {
		return map[x][y];
	}

//	public void printMap() {
//		for (int j = 0; j < YMAX; ++j) {
//			for (int i = 0; i < XMAX; ++i) {
//				print(i, j);
//
//			}
//			System.out.println();
//		}
//	}
//
//	public void print(int x, int y) {
//		Enemy enemy = new Enemy("WALT",3,3);
//		if (enemy.isAtPosition(x, y))
//			System.out.print(MapDrawingConstants.PLAYER_SYMBOL);
//		else {
//			System.out.print(MapDrawingConstants.charFromValue(map[x][y]));
//		}
//	}

	public int getId() {
		return id;
	}

	public void getId(int id) {
		this.id = id;
	}
}
