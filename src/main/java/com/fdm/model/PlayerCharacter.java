package com.fdm.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "CHARACTERS")
public class PlayerCharacter {

	String characterName;
	
	int x;
	int y;
}
