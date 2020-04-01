package com.fdm.model;

public enum Tile {
	FULL_BLOCK('█',true),
	LOWER_WALL('▁',true),
	UPPER_WALL('▔',true),
	LEFT_WALL('▏',true),
	RIGHT_WALL('▕',true),
	COMMA(',',false),
	PERIOD('.',false),
	SPACE(' ',false);
	
	
	
	
	public char theChar() {
        return asChar;
    }
	public boolean isBlocking() {
		return blocking;
	}
	
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
