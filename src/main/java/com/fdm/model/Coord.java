package com.fdm.model;

/**
 * A class used strictly for the Map.validTiles() method
 * Simply an x,y pair
 */
public class Coord {
	public int x;
	public int y;

	public Coord(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}