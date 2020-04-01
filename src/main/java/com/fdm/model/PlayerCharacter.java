package com.fdm.model;

import javax.persistence.*;

import org.springframework.stereotype.Component;

@Component
@Entity
public class PlayerCharacter extends Actor {

	@OneToOne(mappedBy = "playerCharacter")
	Account owner;


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
		this(name, 1, 1, owner);
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
