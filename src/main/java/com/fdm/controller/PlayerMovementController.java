package com.fdm.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.fdm.dal.ActorRepository;
import com.fdm.model.Direction;
import com.fdm.model.PlayerCharacter;

public class PlayerMovementController {
	
	@Autowired
	ActorRepository actorRepository;
	
	PlayerCharacter playerCharacter;
	
	
	public PlayerCharacter moveChar(PlayerCharacter playerCharacter, Direction direction) {
		
		int xPos = playerCharacter.getX();
		int yPos = playerCharacter.getY();
		
		switch(direction) {
		case DOWN:
			playerCharacter.UpdatePosition(xPos, yPos - 1);
			break;
		case UP:
			playerCharacter.UpdatePosition(xPos, yPos + 1);
			break;
		case LEFT:
			playerCharacter.UpdatePosition(xPos + 1, yPos);
			break;
		case RIGHT:
			playerCharacter.UpdatePosition(xPos - 1, yPos);
			break;
		}
		
		return playerCharacter;	
	}
	
	
	
	

}
