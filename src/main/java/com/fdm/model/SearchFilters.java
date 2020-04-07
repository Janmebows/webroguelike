package com.fdm.model;

public class SearchFilters {
	
	private String Name;
	private char Symbol;
	private int level;
	private int killCount;
	private String levelDirection;
	private String killDirection;
		
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public char getSymbol() {
		return Symbol;
	}
	public void setSymbol(char symbol) {
		Symbol = symbol;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getKillCount() {
		return killCount;
	}
	public void setKillCount(int killCount) {
		this.killCount = killCount;
	}
	public String getLevelDirection() {
		return levelDirection;
	}
	public void setLevelDirection(String levelDirection) {
		this.levelDirection = levelDirection;
	}
	public String getKillDirection() {
		return killDirection;
	}
	public void setKillDirection(String killDirection) {
		this.killDirection = killDirection;
	}
	
	

}
