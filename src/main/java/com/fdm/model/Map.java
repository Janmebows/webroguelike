package com.fdm.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "MAPS")
public class Map {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	String mapName = "map";
	transient volatile List<Actor> actorList;

	// The map is defined such that 0,0 is the TOP LEFT CORNER
	private int xMax;
	private int yMax;
	private transient Tile[][] map;

	protected transient static Logger logger = Logger.getLogger("MapLogger");
    private transient volatile char[][] viewMap;
	public Map() {
		this("map");

	}

	public Map(String mapName) {

		this(mapName, new ArrayList<Actor>());
	}

	public Map(String mapName, List<Actor> actors) {
		this.mapName = mapName;
		this.actorList = actors;
		readMapFromFile(mapName);
		viewMap = new char[xMax][yMax];
		logger.info("Generated map, "+ mapName);
	}

    
	//called on backend once a tick to update the map the view will see
    public void updateVisibleMap() {
        for (int y = 0; y < yMax; ++y) {
            for (int x = 0; x < xMax; ++x) {
                viewMap[x][y] = getSymbol(x, y);
            }
        }
    }
	public int getxMax() {
		return xMax;
	}

	public int getyMax() {
		return yMax;
	}

	public static void generateMapFile(int xMax, int yMax, String title) {

		String fileLoc = System.getProperty("user.dir") + "/" + title +".txt";

		logger.trace("Generating map file " + fileLoc);
		try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileLoc),
				StandardCharsets.UTF_8)) {
			writer.append(xMax + "," + yMax + "\n");

			for (int yIndex = 0; yIndex < yMax; ++yIndex) {
				for (int xIndex = 0; xIndex < xMax; ++xIndex) {
					writer.append(".");
				}
				writer.append("\n");

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.fatal("Map file was not found");
		} catch (IOException e) {
			e.printStackTrace();
			logger.fatal("IOException");
		}
		System.out.println(fileLoc);
	}

	public void readMapFromFile() {
		readMapFromFile("map");
	}

	public void readMapFromFile(String mapName) {

		String fileLoc = System.getProperty("user.dir") + "/" + mapName + ".txt";
		Path path = Paths.get(fileLoc);
		logger.trace("Reading map file " + fileLoc);
		try {
			yMax = (int) Files.lines(path).count();
		} catch (IOException x) {
			x.printStackTrace();
			logger.fatal("IOException" + x.getMessage());
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(fileLoc));) {
			String line = reader.readLine();
			// drop the leading BOM character
			if (!Character.isDigit(line.charAt(0)))
				line = line.substring(1);
			String[] xAndy = line.split(","); 
			xMax = Integer.parseInt(xAndy[0]);
			yMax = Integer.parseInt(xAndy[1]);
			map = new Tile[xMax][yMax];
			int yIndex = 0;
			while ((line = reader.readLine()) != null) {
				if (yIndex > yMax) {
					logger.warn("Map file size does not match given dimensions");
					System.out.println("Map file does not match leading dimensions");
					return;
				}
				for (int xIndex = 0; xIndex < xMax; ++xIndex) {
					char c = line.charAt(xIndex);
					map[xIndex][yIndex] = Tile.tileFromChar(c);
				}
				++yIndex;
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
			logger.fatal("IOException" + x.getMessage());
		}

	}

	public void printMap() {
		for (int j = 0; j < yMax; ++j) {
			for (int i = 0; i < xMax; ++i) {
				System.out.print(getSymbol(i, j));
			}
			System.out.println();
		}
	}

	public void addActor(Actor actor) {
		actorList.add(actor);
	}

	public void addActors(List<Actor> actors) {
		this.actorList.addAll(actors);
	}

	public List<Actor> getActors() {
		return actorList;
	}

	public void updateActors() {
		logger.trace(mapName + " updated living actors");
		actorList = actorList.stream().filter(x -> x.isAlive()).collect(Collectors.toList());
	}

	public List<Actor> getEnemies() {
		return actorList.stream().filter(x -> x instanceof Enemy).collect(Collectors.toList());
	}

	public Tile get(int x, int y) {
		return getMap()[x][y];
	}

	public int getId() {
		return id;
	}

	public void getId(int id) {
		this.id = id;
	}

	public Tile[][] getMap() {
		return map;
	}
	
    public char[][] getMapCharacters() {
    	if(viewMap == null) {
    		 viewMap = new char[xMax][yMax];
    		 updateVisibleMap();
    	}
        return viewMap;
    }

    

	public char[] mapAsCharArray() {
		char[] out = new char[(xMax + 1) * yMax];
		for (int y = 0; y < yMax; ++y) {
			for (int x = 0; x < xMax; ++x) {
				out[y * (xMax + 1) + x] = getSymbol(x, y);

			}
			out[y*(xMax+1) + xMax] = '\n';

		}
		return out;

	}

	private char getSymbol(int x, int y) {
		Optional<Actor> playerAt = actorList.parallelStream()
				.filter(player -> player instanceof PlayerCharacter && player.isAtPosition(x, y)).findAny();
		if (playerAt.isPresent())
			return playerAt.get().characterSymbol;
		else {
			Optional<Actor> enemyAt = actorList.parallelStream()
					.filter(enemy -> enemy instanceof Enemy && enemy.isAtPosition(x, y)).findAny();
			if (enemyAt.isPresent()) {
				return enemyAt.get().characterSymbol;

			} else {
				return map[x][y].getChar();
			}
		}
	}

	public boolean isBlocked(int x, int y) {
		if (x < 0 || y < 0 || x >= xMax || y >= yMax)
			return true;
		else
			return map[x][y].isBlocking();
	}

	public boolean tryMoveActor(Actor actor, Direction dir) {
		logger.trace(actor.getCharacterName() + " trying to move " + dir.name());
		int x = actor.getX();
		int y = actor.getY();
		List<Actor> enemyList = getEnemies();
		switch (dir) {
		case DOWN:
			if (!isBlocked(x, y + 1)) {
				List<Actor> enemiesAtPosition = enemyList.stream()
						.filter(enemy -> enemy.isAtPosition(x, y + 1) && enemy.isAlive()).collect(Collectors.toList());
				if (!enemiesAtPosition.isEmpty()) {
					actor.attack((Enemy) enemiesAtPosition.get(0));
				} else {
					actor.updatePosition(x, y + 1);
				}
				return true;
			}
			break;
		case UP:
			if (!isBlocked(x, y - 1)) {
				List<Actor> enemiesAtPosition = enemyList.stream()
						.filter(enemy -> enemy.isAtPosition(x, y - 1) && enemy.isAlive()).collect(Collectors.toList());
				if (!enemiesAtPosition.isEmpty()) {
					actor.attack((Enemy) enemiesAtPosition.get(0));
				} else {
					actor.updatePosition(x, y - 1);
				}
				return true;
			}
			break;
		case LEFT:
			if (!isBlocked(x - 1, y)) {
				List<Actor> enemiesAtPosition = enemyList.stream()
						.filter(enemy -> enemy.isAtPosition(x - 1, y) && enemy.isAlive()).collect(Collectors.toList());
				if (!enemiesAtPosition.isEmpty()) {
					actor.attack((Enemy) enemiesAtPosition.get(0));
				} else {
					actor.updatePosition(x - 1, y);
				}
				return true;
			}
			break;
		case RIGHT:
			if (!isBlocked(x + 1, y)) {
				List<Actor> enemiesAtPosition = enemyList.stream()
						.filter(enemy -> enemy.isAtPosition(x + 1, y) && enemy.isAlive()).collect(Collectors.toList());
				if (!enemiesAtPosition.isEmpty()) {
					actor.attack((Enemy) enemiesAtPosition.get(0));
				} else {
					actor.updatePosition(x + 1, y);
				}
				return true;
			}
			break;
		case NONE:
			return false;
		}
		return false;

	}
}
