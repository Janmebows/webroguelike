package com.fdm.model;

import java.util.Random;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Enemy extends Actor {

	public Enemy() {
		super();
	}

	public Enemy(String characterName, int x, int y, Object key) {
		super();
		this.characterName = characterName;
		this.x = x;
		this.y = y;
		this.characterSymbol = '#';
		this.key = key;
	}

	public Enemy(String name, Object key) {
		this(name, 0, 0,key);
	}

	public Enemy(String characterName, int x, int y) {
		super();
		this.characterName = characterName;
		this.x = x;
		this.y = y;
		this.characterSymbol = '#';
	}

	public Enemy(String name) {
		this(name, 0, 0);
	}

	@Override
	public String toString() {
		return "Enemy [characterName=" + characterName + ", x=" + x + ", y=" + y + "]";
	}

	Random rnd = RandomHolder.getInstance().random;

	@Override
	public boolean move(Direction dir) { 
		
		int next = rnd.nextInt(9);
		switch (next) {
		case 0:
			dir = Direction.LEFT;
			break;
		case 1:
			dir = Direction.RIGHT;
			break;
		case 2:
			dir = Direction.UP;
			break;
		case 3:
			dir = Direction.DOWN;
			break;

		default:
			dir = Direction.NONE;
			break;
		}
		return super.move(dir);
	}

}
