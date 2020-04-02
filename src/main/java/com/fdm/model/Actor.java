package com.fdm.model;

import java.awt.Color;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

@Component
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Actor implements Runnable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	public volatile Object key;
	String characterName;

	char characterSymbol;
	int colorred;
	int colorgreen;
	int colorblue;
	transient Color color = new Color(colorred, colorgreen, colorblue);
	public transient volatile Direction nextInput = Direction.NONE;
	boolean alive = true;
	@ManyToOne
	@JoinColumn(name = "mapid")
	Map map;
	// Stats
	int maxHP = 10;
	int currentHP = maxHP;
	int attack = 10;
	int level = 1;
	int exp = 0;

	public void takeDamage(int damage) {
		currentHP = currentHP - damage;
		if (currentHP <= 0) {
			alive = false;
			System.out.println(characterName + " died");
			map.updateActors();
		}
	}

	public void heal(int amount) {
		currentHP = currentHP + amount;
		if (currentHP > maxHP) {
			currentHP = maxHP;
		}
	}

	public void gainExp(int amount) {
		exp = exp + amount;
		if (exp >= 100) {
			levelup();
		}
	}

	public void levelup() {
		maxHP = maxHP + 10;
		currentHP = maxHP;
		attack = attack + 5;
		level = level + 1;
		System.out.println(characterName + " leveled up!");
		System.out.println(characterName + "'s level is now " + level);
	}

	public void attack(Enemy target) {
		target.takeDamage(attack);
		if (!target.isAlive()) {
			gainExp(100);
		}

	}

	@Override
	public void run() {
		while (alive) {
			try {
				synchronized (key) {
					key.wait();
				}
				if (this.move(nextInput)) {
					this.nextInput = Direction.NONE;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean move(Direction dir) {
		if (dir != Direction.NONE)
			return map.tryMoveActor(this, dir);
		return false;
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

	public void setColor(int red, int green, int blue) {
		this.color = new Color(red, green, blue);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Actor [characterName=" + characterName + ", x=" + x + ", y=" + y + "]";
	}

	public void updatePosition(int x, int y) {
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
