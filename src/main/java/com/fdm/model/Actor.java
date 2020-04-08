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
import com.fdm.controller.GameLogicController;

/**
 * The abstract class Actor contains the base logic and shared information for anything which can exist and move on the map
 * It implements Runnable as all actors will have their own threads
 * @author KILA
 * @version 1.0
 *
 */
@Component
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Actor implements Runnable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String characterName;

	protected transient static Logger logger = Logger.getLogger("ActorLogger");
	/**
	 * The symbol this actor appears with on the map 
	 */
	char characterSymbol;
	
	/**
	 * The redness of this actor
	 * The value must be in [0,255] 
	 */
	int colorred;

	/**
	 * The greenness of this actor
	 * The value must be in [0,255] 
	 */
	int colorgreen;

	/**
	 * The blueness of this actor
	 * The value must be in [0,255] 
	 */
	int colorblue;
	
	/**
	 * The direction this actor is storing to move in next tick
	 */
	public transient volatile Direction nextDirection = Direction.NONE;
	//json ignore because json doesn't like LOB data
	/**
	 * The map this actor exists on
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "mapid")
	Map map;

	// position
	/**
	 * The x coordinate of this actor's position
	 */
	int x;
	/**
	 * The y coordinate of this actor's position
	 */
	int y;

	// Stats
	/**
	 * Whether this actor is alive or not
	 */
	boolean alive = true;
	/**
	 * the maximum HP of this actor
	 */
	int maxHP = 10;
	/**
	 * the current HP of this actor - cannot exceed maxHP
	 * Decreased whenever attacked, and the actor dies when it reaches 0
	 */
	int currentHP = maxHP;
	/**
	 * the amount of HP this actor removes from another actor when attacking
	 */
	int attack = 10;
	/**
	 * The level of the actor - used for leaderboards
	 */
	int level = 1;
	/**
	 * The experience obtained in this actors' level 
	 * A character levels up when this surpasses 100
	 */
	int exp = 0;
	/**
	 * The value of killing this enemy
	 */
	int value = 100;
	/**
	 * The number of kills this actor has - used for leaderboards
	 */
	private int killCount = 0 ;
	
	/**
	 * method for this actor to take damage
	 * the actor will die if the damage exceeds its currentHP - and will add exp to the attacker 
	 * @param damage - the amount of damage to receive
	 * @param attacker - the actor who dealt the attack
	 */
	public void takeDamage(int damage, Actor attacker) {
		logger.trace(this.characterName + " took " + damage + " damage");
		currentHP = currentHP - damage;
		if (currentHP <= 0) {
			alive = false;
			attacker.addKillAndGainExp(this.value*this.level);
			logger.info(characterName + " died");
			map.updateActors();
			GameLogicController.getInstance().addActor(ActorFactory.makeEnemy(map));
		}
	}

	/**
	 * method for healing the actor
	 * @param amount - amount of currentHP to heal for
	 */
	public void heal(int amount) {
		logger.info(this.characterName + " healed for " + amount);
		currentHP = currentHP + amount;
		if (currentHP > maxHP) {
			currentHP = maxHP;
		}

	}
	
	/**
	 * When this actor killed another actor it will gain exp equal to the other's value and the kill count will be incremented
	 * levelUps are called if the exp exceeds 100 after the kill
	 * 
	 * @param amount - amount of exp to add
	 */
	public void addKillAndGainExp(int amount) {
		this.setKillCount(this.getKillCount() + 1);
		logger.info(this.characterName + " gained " + amount + " experience");
		exp = exp + amount;
		while (exp >= 100) {
			levelup();
		}
	}

	/**
	 * Increase stats and decrease exp
	 */
	public void levelup() {
		maxHP = maxHP + 10;
		currentHP = maxHP;
		attack = attack + 5;
		level = level + 1;
		exp -=100;

		logger.info(characterName + " leveled up!");
		logger.info(characterName + "'s level is now " + level);
	}

	/**
	 * called when this hits an enemy. 
	 * Only enemies and their subclasses can be attacked
	 * @param target to deal damage to
	 */
	public void attack(Enemy target) {
		logger.info(this.characterName + " attacked " + target.characterName);
		target.takeDamage(attack, this);

	}

	/**
	 * Boolean to keep the thread running
	 */
	public transient boolean isRunning = false;
	/**
	 * Thread method
	 * waits on a shared key to make a move and then tries to move
	 */
	@Override
	public void run() {
		isRunning = true;
		while (isRunning && alive && GameLogicController.isRunning) {
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
		logger.warn(this.characterName + "'s thread was ended");
		isRunning = false;
		GameLogicController.getInstance().removeActor(this);
	}

	/**
	 * @return the string including html tags, color and the actor's symbol
	 */
	public String getHtmlString() {
		return "<p style=\"padding: 0; margin: 0; color: " + getColor() + ";\">" + characterSymbol + "</p>";
	}

	/**
	 * Tries to move in direction dir
	 * @param dir the direction to move in, ignored if NONE
	 * @return boolean if successfully moved
	 */
	public boolean move(Direction dir) {
		if (dir != Direction.NONE)
			return map.tryMoveActor(this, dir);
		return false;
	}

	/**
	 * @return the colour as a hexadecimal string #XXXXXX
	 */
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

	/**
	 * Updates the position of this actor to the new position [x,y]
	 * @param x - new x 
	 * @param y - new y
	 */
	public void updatePosition(int x, int y) {

		logger.info(this.characterName + " moves from " + getPositionString() + " to " + getPositionString(x, y) + "!");
		this.x = x;
		this.y = y;
	}

	/**
	 * @return a nicely formatted position string
	 */
	public String getPositionString() {
		return "[" + x + ", " + y + "]";
	}

	private String getPositionString(int x, int y) {
		return "[" + x + ", " + y + "]";
	}

	/**
	 * Check if this is at position [x,y]
	 * @param x - x position to compare to
	 * @param y - y position to compare to
	 * @return boolean true if this is at the position [x,y] else false
	 */
	public boolean isAtPosition(int x, int y) {
		return (this.x == x) && (this.y == y);
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		logger.info(this.characterName + " changed name to " + characterName + "!");
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

	public int getKillCount() {
		return killCount;
	}

	public void setKillCount(int killCount) {
		this.killCount = killCount;
	}
}
