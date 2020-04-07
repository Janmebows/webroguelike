package com.fdm.model;

import javax.persistence.*;

import org.springframework.stereotype.Component;

@Component
@Entity
public class PlayerCharacter extends Actor {

//	@OneToOne(mappedBy = "playerCharacter")
	// Account owner;
	public volatile char nextInput = '\u0000';

	public void setInput(char input) {
		// if null character or blankspace
		if (input == '\u0000' || input == ' ') {
			return;
		}
		// System.out.println("playerCharacter " + characterName + " inputted " +
		// input);

		Direction inputDirection = processInput(input);
		if (inputDirection != Direction.NONE)
			nextDirection = inputDirection;
		return;
	}
//
//	@Override
//	public boolean move(Direction dir) {
//		processInput(nextInput);
//		nextInput = '\u0000';
//		return super.move(dir);
//	}

	public Direction processInput(char input) {
		// currently using wasd.
		switch (input) {
		case 'w':
			return Direction.UP;
		case 's':
			return Direction.DOWN;
		case 'a':
			return Direction.LEFT;
		case 'd':
			return Direction.RIGHT;
		}
		return Direction.NONE;
	}

	public PlayerCharacter() {
		super();
	}

	public PlayerCharacter(String characterName, int x, int y, Object key) {

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

	public void setKey(Object key) {
		this.key = key;

	}

}
