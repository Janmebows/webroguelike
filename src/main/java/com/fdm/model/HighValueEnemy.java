package com.fdm.model;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Component
@Entity
public class HighValueEnemy extends Enemy{
	public HighValueEnemy(String characterName, int x, int y) {
		super();
		this.characterName = characterName.toUpperCase();
		this.x = x;
		this.y = y;
		this.characterSymbol = '*';
		this.colorred = 0;
		this.colorgreen = 255;
		this.colorblue = 0;
		this.value = 1000;
		logger.info("Summoned new high value enemy " + this.toString());
	}
	public HighValueEnemy() {}
}
