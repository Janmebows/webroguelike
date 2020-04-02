package com.fdm.controller;

import java.util.Scanner;

import com.fdm.model.Direction;
import com.fdm.model.PlayerCharacter;


public class PlayerCharacterInputController implements Runnable {
	
	PlayerCharacter playerCharacter;
	private boolean isRunning;
//	private String bufferInput;

	Scanner scanner = new Scanner(System.in);
	
	public PlayerCharacterInputController(PlayerCharacter playerCharacter) {
		super();
		this.playerCharacter = playerCharacter;
		isRunning = true;
	} 

	@Override
	public void run() {
		while (isRunning) {
			handleInput();
		}
	}
//	public String getUserInput() {
//		String temp = bufferInput;
//		bufferInput = null;
//		return temp;
//	}
//	public void setInput() {
//		bufferInput = scanner.nextLine();
//	}

	String getInput() {
		return scanner.nextLine();
	}
	private void handleInput() {
		String input = getInput();
		if (input == null || input.equals("")){
			return;
		}
		System.out.println(input);
		if (input.equalsIgnoreCase("Q")) {
			System.out.println("Quitting");
			isRunning = false;
		} 
		Direction inputDirection = processInput(input);
		if(inputDirection!= Direction.NONE)
			playerCharacter.nextInput = inputDirection;
		System.out.println("Inputted "+input);
		return;
	}
	
	public Direction processInput(String input) {
		// currently using wasd.
		switch (input) {
		case "w":
			return Direction.UP;
		case "s":
			return Direction.DOWN;
		case "a":
			return Direction.LEFT;
		case "d":
			return Direction.RIGHT;
		}
		return Direction.NONE;
	}
}
