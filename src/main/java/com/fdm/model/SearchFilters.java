package com.fdm.model;

public class SearchFilters {
	
	private String name;
	private String symbol;
	private int level;
	private int killCount;
	private String levelDirection;
	private String killDirection;
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
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
