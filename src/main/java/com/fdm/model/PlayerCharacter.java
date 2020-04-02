package com.fdm.model;

import javax.persistence.*;

import org.springframework.stereotype.Component;

@Component
@Entity
public class PlayerCharacter extends Actor {

//	@OneToOne(mappedBy = "playerCharacter")
	//Account owner;

	public PlayerCharacter() {
		super();
	}

	public PlayerCharacter(String characterName, int x, int y,  Object key) {

		super();
		this.characterName = characterName;
		this.x = x;
		this.y = y;
		this.key = key;
	}
	public PlayerCharacter(String characterName, int x, int y) {

		super();
		this.characterName = characterName;
		this.x = x;
		this.y = y;
	}


	public PlayerCharacter(String name, Object key) {
		this(name, 1, 1, key);
	}


	public PlayerCharacter(String name) {
		this(name, 1, 1);
	}

	@Override
	public String toString() {
		return "PlayerCharacter [characterName=" + characterName + ", x=" + x + ", y=" + y + "]";
	}

//	public Account getOwner() {
//		return owner;
//	}
//
//	public void setOwner(Account owner) {
//		this.owner = owner;
//	}

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
