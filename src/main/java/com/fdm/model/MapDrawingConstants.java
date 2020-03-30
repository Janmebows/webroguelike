package com.fdm.model;

public final class MapDrawingConstants {
	public final static char PLAYER_SYMBOL = '@';
	public final static char EMPTY_SYMBOL = '.';
	public final static char WALL_SYMBOL = '|';
	
	
	
	
	private MapDrawingConstants() {
		
	}
	
	public static char charFromValue(int val) {
		switch(val) {
		case 1: return WALL_SYMBOL;
		case 0: return EMPTY_SYMBOL;
		}
		return ' ';
	}
	
	
}
