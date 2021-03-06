package com.fdm.model;

import java.util.Random;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

/**
 * An extension of {@link Actor}
 * Specifically  
 * @author KILA
 * @version 1.0
 *
 */
@Component
@Entity
public class Enemy extends Actor {

	public Enemy() {
		super();
	}

	/**
	 * Construct an enemy
	 * @param characterName - the name to give the enemy
	 * @param x - the x coord to spawn at
	 * @param y - the y coord to spawn at
	 */
	public Enemy(String characterName, int x, int y) {
		super();
		this.characterName = characterName;
		this.x = x;
		this.y = y;
		this.characterSymbol = '#';
		this.colorred = 255;
		this.colorgreen= 255;
		this.colorblue = 255;
		
		logger.info("Summoned new enemy " + this.toString());
	}

	public Enemy(String name) {
		this(name, 1, 1);
	}

	@Override
	public String toString() {
		return "Enemy [characterName=" + characterName + ", x=" + x + ", y=" + y + "]";
	}

	transient Random rnd = RandomHolder.getInstance().random;

	/**
	 * An override for {@link Actor#move(Direction)}
	 * It randomly grabs a direction to move in
	 * @param dir - ignored
	 */
	@Override
	public boolean move(Direction dir) {

		int next = rnd.nextInt(20);
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
