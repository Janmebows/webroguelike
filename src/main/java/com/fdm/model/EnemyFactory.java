package com.fdm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fdm.model.Map.Coord;

public class EnemyFactory {

	Random rng = RandomHolder.getInstance().random;

	public Enemy makeEnemy(Map map) {
		List<Coord> coords = map.validTiles();
		Coord coord = coords.get(rng.nextInt(coords.size()));
		Enemy enemy = new Enemy(generateName(), coord.x, coord.y);
		return enemy;
	}

	public List<Actor> makeEnemies(Map map, int count) {
		List<Actor> enemyList = new ArrayList<Actor>();
		for (int i = 0; i < count; ++i) {
			enemyList.add(makeEnemy(map));

		}
		return enemyList;
	}


	public static final transient String[] names = new String[] { "Dwight Schrute", "Bob the boss", "Benny Brooks",
			"Dave Patrick", "Conner Livingston", "Teddie Roads", "James Fox", "Junior Freedom", "Angel Duke",
			"Devin Gates", "Sam Living", "Bryan Cross", "Ackley", "Citrine", "Hayley", "Aspen", "Amethyst", "Clearbay",
			"Cloudrun", "Oakenrun", "Fogside", "Roseside", };

	public String generateName() {
		return names[rng.nextInt(names.length)];
	}
}
