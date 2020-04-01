package com.fdm.model;

import java.awt.Color;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.stereotype.Component;

@Component
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Actor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	String characterName;

	char characterSymbol;
	int colorred;
	int colorgreen;
	int colorblue;
	transient Color color = new Color(colorred,colorgreen,colorblue);
	
	boolean alive = true;
	
	//Stats
	int maxHP = 10;
	int currentHP = maxHP;
	int attack = 10;
	int level = 1;
	int exp = 0;
	
	public void takeDamage(int damage) {
		currentHP = currentHP - damage;
		if (currentHP <= 0) {
			alive = false;
		}
	}
	
	public void heal(int amount) {
		currentHP = currentHP + amount;
		if(currentHP > maxHP) {
			currentHP = maxHP;
		}
	}
	
	public void gainExp(int amount) {
		exp = exp + amount;
		if(exp >= 100) {
			levelup();
		}
	}
	
	public void levelup() {
		maxHP = maxHP + 10;
		currentHP = maxHP;
		attack = attack + 5;
		level = level + 1;
		System.out.println("You've leveled up!");
		System.out.println("You're level is now " + level);
	}
	
	public void attack(Enemy target) {
		target.takeDamage(attack);
		if(!target.isAlive()) {
			gainExp(100);
		}
				
	}

	public int getColorred() {
		return colorred;
	}

	public int getColorgreen() {
		return colorgreen;
	}

	public int getColorblue() {
		return colorblue;
	}

	public boolean isAlive() {
		return alive;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public int getAttack() {
		return attack;
	}

	public int getLevel() {
		return level;
	}

	public int getExp() {
		return exp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char getCharacterSymbol() {
		return characterSymbol;
	}

	public void setCharacterSymbol(char characterSymbol) {
		this.characterSymbol = characterSymbol;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}


	@Override
	public String toString() {
		return "Actor [characterName=" + characterName + ", x=" + x + ", y=" + y + "]";
	}

	public void UpdatePosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean isAtPosition(int x, int y) {
		return (this.x == x) && (this.y == y);
	}

	// position
	int x;
	int y;

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
