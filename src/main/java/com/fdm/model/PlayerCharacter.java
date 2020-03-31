package com.fdm.model;

import javax.persistence.*;

import org.springframework.stereotype.Component;

@Component
@Entity
public class PlayerCharacter extends Actor {

	@OneToOne(mappedBy = "playerCharacter")
	Account owner;
	
	@ManyToOne
	@JoinColumn(name = "id")
	Map map;
	

	public PlayerCharacter() {
		super();
	}

	public PlayerCharacter(String characterName, int x, int y, Account owner) {
		super();
		this.characterName = characterName;
		this.x = x;
		this.y = y;
		this.owner = owner;
	}

	public PlayerCharacter(String name, Account owner) {
		this(name, 0, 0, owner);
	}

	public boolean moveChar(Direction direction) {

		int xPos = this.getX();
		int yPos = this.getY();
		
		switch (direction) {
		case DOWN:
			if(map.map[xPos][yPos + 1] == 0) {
				this.UpdatePosition(xPos, yPos + 1);
				return true;
			}
			break;
		case UP:
			if(map.map[xPos][yPos - 1] == 0) {
				this.UpdatePosition(xPos, yPos - 1);
				return true;
			}
			break;
		case LEFT:
			if(map.map[xPos - 1][yPos] == 0) {
				this.UpdatePosition(xPos - 1, yPos);
				return true;
			}	
			break;
		case RIGHT:
			if(map.map[xPos + 1][yPos] == 0) {
				this.UpdatePosition(xPos + 1, yPos);
				return true;
			}
			break;
		}

		return false;
	}

	@Override
	public String toString() {
		return "PlayerCharacter [characterName=" + characterName + ", x=" + x + ", y=" + y + "]";
	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}
