package com.fdm.model;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Component
@Entity
public class StrongEnemy extends Enemy {
	public StrongEnemy(String characterName, int x, int y) {
		super();
		this.characterName = characterName.toUpperCase();
		this.x = x;
		this.y = y;
		this.characterSymbol = 'ø';
		this.colorred = 255;
		this.colorgreen = 0;
		this.colorblue = 0;
		this.maxHP = 20;
		this.value = 100;
		logger.info("Summoned new strong enemy " + this.toString());
	}
	public StrongEnemy() {}
}
