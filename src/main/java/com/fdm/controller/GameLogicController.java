package com.fdm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.fdm.model.Actor;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

public class GameLogicController implements Runnable {

	public static final long SERVER_TICK = 500;
	Map map;

	// if we want multiple maps this shouldn't be static
	public static boolean isRunning = false;
	private static volatile Object key;
	private List<Actor> actorList;

	public GameLogicController(Map map, List<Actor> actorList) {
		super();
		this.map = map;
		GameLogicController.key = key;
		GameLogicController.instance = this;
		this.actorList = actorList;
//		actorList.forEach(x -> x.key = getKey());
		instance = this;
		isRunning = true;
		Logger.getLogger("RootLogger").warn("A new logic controller was made since we didn't make it a singleton!");
	}

	private GameLogicController() {
	}

	static GameLogicController instance;

	public static GameLogicController getInstance() {
		if (instance == null)
			instance = new GameLogicController();
		return instance;
	}

	public Actor findActor(int id) {
		List<Actor> valid = actorList.parallelStream().filter(x -> x.getId() == id).collect(Collectors.toList());
		if(valid.size()==1)
			return valid.get(0);
		else return null;
		
	}
	public static Object getKey() {
		if (key == null)
			key = new Object();
		return key;
	}
	public void tryAddActor(PlayerCharacter actor) {
		if(findActor(actor.getId()) == null)
			return;
		else {
			addActor(actor);
		}
	
	}

	public void addActor(Actor newActor) {
//		newActor.key = getKey();
		newActor.setMap(map);
		map.addActor(newActor);
		actorList.add(newActor);
		Thread th = new Thread(newActor);
		th.start();
	}

	
//	public boolean startGame(Map map, List<Actor> actorList) {
//		if(isRunning)
//			return false;
//		this.map = map;
//		GameLogicController.key = key;
//		GameLogicController.instance = this;
//		this.actorList = actorList;
//		actorList.forEach(x -> x.key = getKey());
//		instance = this;
//		
//		return true;
//	}
	
	public void runConsoleGame() {
		map.printMap();
		for (Actor actor : actorList) {
			Thread th = new Thread(actor);
			th.start();
		}
		while (isRunning) {

			synchronized (getKey()) {
				getKey().notifyAll();
			}
			map.printMap();
			// MapUpdate(x, y, newSymbol)

			try {
				Thread.sleep(SERVER_TICK / 2);
				map.updateVisibleMap();
				Thread.sleep(SERVER_TICK / 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// wake up threads so they can die
		synchronized (getKey()) {
			getKey().notifyAll();
		}
	}

	public void runGame() {
		for (Actor actor : actorList) {
			Thread th = new Thread(actor);
			th.start();
		}
		while (isRunning) {

			synchronized (getKey()) {
				map.updateVisibleStringMap();
				getKey().notifyAll();
			}
			// MapUpdate(x, y, newSymbol)

			try {
				Thread.sleep(SERVER_TICK);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		// wake up threads so they can die
		synchronized (getKey()) {
			getKey().notifyAll();
		}
	}

	@Override
	public void run() {
		runGame();
		runConsoleGame();
	}



}