package com.fdm.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
//	public static final int XMAX = 5;
//	public static final int YMAX = 5;
	String mapName = "map";
	private int xMax;
	private int yMax;

	public int getxMax() {
		return xMax;
	}

	public int getyMax() {
		return yMax;
	}

	public static void generateMapFile(int xMax, int yMax, String title) {
		String fileLoc = System.getProperty("user.dir") + "\\" + title +".txt";
		try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileLoc),
				StandardCharsets.UTF_8)) {
			writer.append(xMax + "," + yMax + "\n");

			for (int xIndex = 0; xIndex < xMax; ++xIndex) {
				for (int yIndex = 0; yIndex < yMax; ++yIndex) {
				writer.append(".");
				}
				writer.append("\n");

			}
			// do stuff
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(fileLoc);
	}

	// its will correspond to cell types for now - we may move this to a class
	// 1 . walls
	// 0 . passable
	private Tile[][] map;
	// PlayerCharacter character = new PlayerCharacter("Janme", 6, 2, new
	// Account());

	public Map() {
		this("map");
//		makeMap();

	}
	public Map(String mapName) {
		this.mapName=mapName;
		readMapFromFile(mapName);
//		makeMap();

	}
	
	public void readMapFromFile(String mapName){
		String fileLoc = System.getProperty("user.dir") + "\\" + mapName + ".txt";
		Path path = Paths.get(fileLoc);
		try {
			yMax = (int) Files.lines(path).count();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(fileLoc, StandardCharsets.UTF_8));) {
			String line = reader.readLine();
			// drop the leading BOM character
			//line = line.substring(1);
			String[] xAndy = line.split(",");
			char[] x = xAndy[0].toCharArray();
			xMax = Integer.parseInt(xAndy[0]);
			yMax = Integer.parseInt(xAndy[1]);
			map = new Tile[xMax][yMax];
			int yIndex = 0;
			while ((line = reader.readLine()) != null) {
				if (yIndex > yMax) {
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
		}

	}

	public void readMapFromFile() {
		readMapFromFile("map");
	}

//	private void makeMap() {
//		for (int j = 0; j < yMax; ++j) {
//			for (int i = 0; i < xMax; ++i) {
//				getMap()[i][j] = j == 0 || j == yMax - 1 || i == 0 || i == xMax- 1 ? 1 : 0;
//			}
//		}
//	}

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
}
