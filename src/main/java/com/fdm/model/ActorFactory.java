package com.fdm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import com.fdm.model.Map.Coord;

public class ActorFactory {
	
	static Random rng = RandomHolder.getInstance().random;
	static Logger logger = Logger.getLogger("actorLogger");
	public static Enemy makeEnemy(Map map) {
		List<Coord> coords = map.validTiles();
		Coord coord = coords.get(rng.nextInt(coords.size()));
		int choice = rng.nextInt(12);
		Enemy enemy;
		switch (choice) {
		case 0:
			enemy = new HighValueEnemy(generateName(),coord.x,coord.y);
			break;
		case 1:
			enemy = new LowValueEnemy(generateName(),coord.x,coord.y);
			break;
		case 2:
			enemy = new StrongEnemy(generateName(),coord.x,coord.y);
			break;
		default:
			enemy = new Enemy(generateName(),coord.x,coord.y);
			break;
		}
		logger.info("Spawned new enemy "+ enemy);
		return enemy;
	}
	
	public static  PlayerCharacter makePlayerCharacter(String name, Map map) {
		List<Coord> coords = map.validTiles();
		Coord coord = coords.get(rng.nextInt(coords.size()));
		PlayerCharacter plc = new PlayerCharacter(name,coord.x,coord.y);
		plc.setCharacterSymbol(name.charAt(0));
		plc.setColor(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
		logger.info("Generated new player "+ plc);
		return plc;
	}
	public static  List<Actor> makeEnemies(Map map, int count) {
		List<Actor> enemyList = new ArrayList<Actor>();
		for (int i = 0; i < count; ++i) {
			enemyList.add(makeEnemy(map));

		}
		logger.info("Finished generating enemies");
		return enemyList;
	}


	public static final transient String[] names = new String[] { "Dwight Schrute", "Bob the boss", "Benny Brooks",
			"Dave Patrick", "Conner Livingston", "Teddie Roads", "James Fox", "Junior Freedom", "Angel Duke",
			"Devin Gates", "Sam Living", "Bryan Cross", "Ackley", "Citrine", "Hayley", "Aspen", "Amethyst", "Clearbay",
			"Cloudrun", "Oakenrun", "Fogside", "Roseside", };

	public static String generateName() {
		return names[rng.nextInt(names.length)];
	}
}
