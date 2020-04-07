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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fdm.controller.GameController;
import com.fdm.controller.GameLogicController;

@Component
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Actor implements Runnable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
//	public volatile transient Object key;
	String characterName;

	protected transient static Logger logger = Logger.getLogger("ActorLogger");
	char characterSymbol;
	int colorred;
	int colorgreen;
	int colorblue;
	public transient volatile Direction nextDirection = Direction.NONE;
	//json ignore because json doesn't like LOB data
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "mapid")
	Map map;

	// position
	int x;
	int y;

	// Stats
	boolean alive = true;
	int maxHP = 10;
	int currentHP = maxHP;
	int attack = 10;
	int level = 1;
	int exp = 0;

	public void takeDamage(int damage) {
		logger.info(this.characterName + " took " + damage + " damage");
		currentHP = currentHP - damage;
		if (currentHP <= 0) {
			alive = false;
			System.out.println(characterName + " died");
			logger.warn(characterName + " died");
			map.updateActors();
		}
	}

	public void heal(int amount) {
		logger.info(this.characterName + " healed for " + amount);
		currentHP = currentHP + amount;
		if (currentHP > maxHP) {
			currentHP = maxHP;
		}

	}

	public void gainExp(int amount) {
		logger.info(this.characterName + " gained " + amount + " experience");
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
		logger.info(characterName + " leveled up!");
		logger.info(characterName + "'s level is now " + level);
	}

	public void attack(Enemy target) {
		logger.info(this.characterName + " attacked " + target.characterName);
		target.takeDamage(attack);
		if (!target.isAlive()) {
			gainExp(100);
		}

	}

	public transient boolean isRunning = false;
	@Override
	public void run() {
		isRunning = true;
		while (alive && GameLogicController.isRunning) {
			try {
				synchronized ( GameLogicController.getKey()) {
					 GameLogicController.getKey().wait();
				}
				if (this.move(nextDirection)) {

					logger.info(this.characterName + " moved " + nextDirection.name() + " new position"
							+ getPositionString());
					this.nextDirection = Direction.NONE;

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isRunning = false;
	}

	public String getHtmlString() {
		return "<p style=\"padding: 0; margin: 0; color: " + getColor() + ";\">" + characterSymbol + "</p>";
	}

	public boolean move(Direction dir) {
		if (dir != Direction.NONE)
			return map.tryMoveActor(this, dir);
		return false;
	}

	public String getColor() {
		Color color = new Color(colorred, colorgreen, colorblue);
		return "#" + Integer.toHexString(color.getRGB()).substring(2);

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

	public void setColor(int red, int green, int blue) {
		this.colorred = red;
		this.colorgreen = green;
		this.colorblue = blue;
	}

	@Override
	public String toString() {
		return "Actor [characterName=" + characterName + getPositionString() + "]";
	}

	public void updatePosition(int x, int y) {

		logger.info(this.characterName + " moves from " + getPositionString() + " to " + getPositionString(x, y) + "!");
		this.x = x;
		this.y = y;
	}

	private String getPositionString() {
		return "[" + x + ", " + y + "]";
	}

	private String getPositionString(int x, int y) {
		return "[" + x + ", " + y + "]";
	}

	public boolean isAtPosition(int x, int y) {
		return (this.x == x) && (this.y == y);
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		logger.info(this.characterName + " becomes " + characterName + "!");
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

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}
