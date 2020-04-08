package com.fdm.model;

/**
 * An enum for map information
 * an enum with a character and boolean
 * The character is the appearance on the map and the boolean is if the tile is blocking
 * @author KILA
 * @version 1.0
 *
 */
public enum Tile {
	FULL_BLOCK('█',true),
	LOWER_WALL('▁',true),
	UPPER_WALL('▔',true),
	LEFT_WALL('▏',true),
	RIGHT_WALL('▕',true),
	COMMA(',',false),
	PERIOD('.',false),
	SPACE(' ',false);
	
	
	
	
	/**
	 * @return the character to display
	 */
	public char getChar() {
        return asChar;
    }
	/**
	 * @return the boolean true if an actor cannot pass through this block
	 */
	public boolean isBlocking() {
		return blocking;
	}
	
	/**
	 * Given a character, obtain a Tile
	 * @param c the character to find
	 * @return the tile corresponding to that character
	 */
	public static Tile tileFromChar(char c) {
		for(Tile t : Tile.values()) {
			if(t.asChar == c)
				return t;
		}
		return FULL_BLOCK;
	}
    private final char asChar;
	private final boolean blocking;

    Tile(char asChar, boolean blocking) {
        this.asChar = asChar;
        this.blocking = blocking;
    }

}
