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

	public PlayerCharacter(String characterName, Map map, int x, int y, Account owner, Object key) {
		super();
		this.characterName = characterName;
		this.map = map;
		this.x = x;
		this.y = y;
		this.owner = owner;
		this.key = key;

	}

	public PlayerCharacter(String name, Map map, Account owner, Object key) {
		this(name, map, 1, 1, owner, key);
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
