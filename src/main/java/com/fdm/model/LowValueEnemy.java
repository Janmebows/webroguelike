package com.fdm.model;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Component
@Entity
public class LowValueEnemy extends Enemy{
	public LowValueEnemy(String characterName, int x, int y) {
		super();
		this.characterName = characterName;
		this.x = x;
		this.y = y;
		this.characterSymbol = '¢';
		this.colorred = 164;
		this.colorgreen = 102;
		this.colorblue = 40;
		this.value = 10;
		logger.info("Summoned new low value enemy " + this.toString());
	}
	public LowValueEnemy() {}
}
