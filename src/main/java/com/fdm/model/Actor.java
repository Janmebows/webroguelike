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
	
	@ManyToOne
	@JoinColumn(name = "mapid")
	Map map;
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
