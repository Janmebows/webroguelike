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

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * Class of Map data, allows for generation of map UTF-8 files 
 * @author KILA
 * @version 1.0
 */
@Component
@Entity
@Table(name = "MAPS")
public class Map {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	String mapName = "map";
	@ElementCollection
    @CollectionTable(name="ACTORS_ON_MAP")
	volatile List<Actor> actorList;

	// The map is defined such that 0,0 is the TOP LEFT CORNER
	private int xMax;
	private int yMax;
	@Lob
	@Column(name = "TILES", columnDefinition="BLOB")
	private Tile[][] map;

	protected transient static Logger logger = Logger.getLogger("MapLogger");

	private transient volatile String[][] stringViewMap;

	public Map() {

	}

	/**
	 * Construct a map without actors
	 * Overload of {@link #Map(String, List)}
	 * @param mapName the name of the text file to read
	 */
	public Map(String mapName) {

		this(mapName, new ArrayList<Actor>());
	}
	/**
	 * Construct a map with actors
	 * @param mapName the name of the text file to read
	 * @param actors the actors who will immediately exist on the map
	 */
	public Map(String mapName, List<Actor> actors) {
		this.mapName = mapName;
		this.actorList = actors;
		readMapFromFile(mapName);
		logger.info("Generated map, " + mapName);
	}

	
	
	/**
	 * Updates the web visible map which contains strings 
	 * The strings include html tags to allow for styling
	 */
	public void updateVisibleStringMap() {
		stringViewMap = new String[xMax][yMax];
		for (int y = 0; y < yMax; ++y) {
			for (int x = 0; x < xMax; ++x) {
				stringViewMap[x][y] = getStringSymbol(x, y);
			}
		}
	}

	/**
	 * Gets the string map for display in html
	 * It will generate the map if it does not exist
	 * @return the string map generated by {@link #updateVisibleStringMap()} it contains html tags for styling
	 */
	public String[][] getStringMap(){
		if(stringViewMap == null){
			updateVisibleStringMap();
		}
		return stringViewMap;
	}

	/**
	 * @return the number of x points on the map
	 */
	public int getxMax() {
		return xMax;
	}

	/**
	 * @return the number of y points on the map
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Generates a txt file with utf-8 encoding of a map
	 * The map will be default generated with walls on the outside and dots on the inside
	 * @param xMax - number of x points for the map to generate
	 * @param yMax - number of y points for the map to generate 
	 * @param title - the name of the map
	 */
	public static void generateMapFile(int xMax, int yMax, String title) {

		String fileLoc = System.getProperty("user.dir") + "/" + title + ".txt";

		logger.trace("Generating map file " + fileLoc);
		try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileLoc),
				StandardCharsets.UTF_8)) {
			writer.append(xMax + "," + yMax + "\n");

			for (int yIndex = 0; yIndex < yMax; ++yIndex) {
				for (int xIndex = 0; xIndex < xMax; ++xIndex) {
					if(yIndex == 0 || yIndex == yMax-1 || xIndex ==0 || xIndex == xMax -1)
						writer.append('█');
						else
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

	/**
	 * Overload for {@link Map#readMapFromFile(String)}
	 * Reads the default map
	 */
	public void readMapFromFile() {
		readMapFromFile("ProductionMap");
	}

	/**
	 * Loads the map in the folder/mapName and sets the attributes of this map to those values
	 * @param mapName - name of map to read in
	 */
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
				if (yIndex >= yMax) {
					logger.error("Map file size does not match given dimensions");
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


	/**
	 * Add actor to map
	 * @param actor to add
	 */
	public void addActor(Actor actor) {
		actorList.add(actor);
	}

	/**
	 * Remove actor from map if they are present
	 * @param id the id of the actor to remove
	 */
	public void remove(int id) {
		actorList.removeIf(x -> x.getId() == id);
		
	}
	
	/**
	 * Add actors to map
	 * @param actors the list of actors to add
	 */
	public void addActors(List<Actor> actors) {
		actors.forEach(x -> x.setMap(this));
		this.actorList.addAll(actors);
	}
	/**
	 * Get actors present on map
	 * @return The actors
	 */
	public List<Actor> getActors() {
		return actorList;
	}
	
	/**
	 * Update list of actors
	 * This removes any actors who have died
	 */
	public void updateActors() {
		logger.trace(mapName + " updated living actors");
		actorList = actorList.stream().filter(x -> x.isAlive()).collect(Collectors.toList());
	}
	/**
	 * Get enemies 
	 * @return the actors who are instances of Enemy
	 */
	public List<Actor> getEnemies() {
		return actorList.stream().filter(x -> x instanceof Enemy).collect(Collectors.toList());
	}

	/**
	 * Get the tile at coordinate [x,y]
	 * @param x - the x coordinate
	 * @param y - the y coordinate 
	 * @return the Tile object
	 */
	public Tile get(int x, int y) {
		return getMap()[x][y];
	}

	
	/**
	 * @return the id of the map
	 */
	public int getId() {
		return id;
	}

	
	/**
	 * @return the Tile[][] the map uses
	 */
	public Tile[][] getMap() {
		return map;
	}


	/**
	 * A synchronised method to get the symbol the map should show at coordinate [x,y]
	 * It prioritises players, then enemies, then tiles
	 * @param x - the x coordinate
	 * @param y - the y coordinate
	 * @return the string of the html for that coordinate pair
	 */
	private synchronized String getStringSymbol(int x, int y) {
		Optional<Actor> playerAt = actorList.parallelStream()
				.filter(player -> player instanceof PlayerCharacter && player.isAtPosition(x, y)).findAny();
		if (playerAt.isPresent())
			return playerAt.get().getHtmlString();
		else {
			Optional<Actor> enemyAt = actorList.parallelStream()
					.filter(enemy -> enemy instanceof Enemy && enemy.isAtPosition(x, y)).findAny();
			if (enemyAt.isPresent()) {
				return enemyAt.get().getHtmlString();

			} else {
				return "<p style=\"padding: 0; margin: 0;\">" + map[x][y].getChar() + "</p>";
			}
		}
	}

	
	/**
	 * Function to tell if the coordniate [x,y] is accessible
	 * @param x - the x coordinate
	 * @param y - the y coordinate
	 * @return boolean true if the tile is inaccessible, false if accessible
	 */
	public boolean isBlocked(int x, int y) {
		if (x < 0 || y < 0 || x >= xMax || y >= yMax) {
			logger.error("Something tried to escape bounds with [" + x + ", "+y + "]");
			return true;
		}
		else
			return map[x][y].isBlocking();
	}

	
	/**
	 * Function for a character to try and move on this map
	 * Moving into an enemy will instead attack them
	 * @param actor to move
	 * @param dir Direction to move in
	 * @return a boolean for if the move was successful. Will return true if an enemy was attacked
	 */
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


	/**
	 * Function to find all allowable spawn locations
	 * @return a List of Coords ([x,y] pairs) where Actors can be spawned
	 */
	public List<Coord> validTiles() {
		List<Coord> validTiles = new ArrayList<Coord>();
		for (int y = 0; y < yMax; ++y) {
			for (int x = 0; x < xMax; ++x) {
				if(!map[x][y].isBlocking())
					validTiles.add(new Coord(x,y));
			}
		}
		return validTiles;
	}
	



}
