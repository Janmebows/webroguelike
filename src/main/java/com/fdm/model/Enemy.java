package com.fdm.model;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Enemy extends Actor {

	public Enemy() {
		super();
	}

	public Enemy(String characterName, int x, int y) {
		super();
		this.characterName = characterName;
		this.x = x;
		this.y = y;
	}

	public Enemy(String name) {
		this(name, 0, 0);
	}

	@Override
	public String toString() {
		return "Enemy [characterName=" + characterName + ", x=" + x + ", y=" + y + "]";
	}

}
