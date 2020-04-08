package com.fdm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.fdm.model.Actor;
import com.fdm.model.ActorFactory;
import com.fdm.model.Enemy;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

public class GameLogicController implements Runnable {

	public static final long SERVER_TICK = 500;
	private Map map;

	// if we want multiple maps this shouldn't be static
	public static boolean isRunning = false;
	private static volatile Object key;
	private List<Actor> actorList;

	public GameLogicController(Map map, List<Actor> actorList) {
		super();
		this.setMap(map);
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

	public static GameLogicController instance;

	public static GameLogicController getInstance() {
		if (instance == null)
			instance = new GameLogicController();
		return instance;
	}

	public Actor findActor(int id) {
		List<Actor> valid = actorList.parallelStream().filter(x -> x.getId() == id).collect(Collectors.toList());
		if (valid.size() == 1)
			return valid.get(0);
		else
			return null;

	}

	public static Object getKey() {
		if (key == null)
			key = new Object();
		return key;
	}

	public void tryAddActor(PlayerCharacter actor) {

		if (findActor(actor.getId()) != null)

			return;
		else {
			addActor(actor);
		}

	}

	public void addActor(Actor newActor) {
		newActor.setMap(getMap());
		getMap().addActor(newActor);
		actorList.add(newActor);
		Thread th = new Thread(newActor);
		th.start();
	}

	public void removeActor(Actor actor) {
		actor.isRunning = false;
		getMap().remove(actor.getId());
		actorList.removeIf(x -> x.getId() == actor.getId());
		if(actor instanceof Enemy)
			addActor(ActorFactory.makeEnemy(getMap()));
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
		getMap().updateVisibleMap();
		getMap().printMap();
		for (Actor actor : actorList) {
			Thread th = new Thread(actor);
			th.start();
		}
		while (isRunning) {

			synchronized (getKey()) {
				getKey().notifyAll();
			}
			getMap().printMap();
			// MapUpdate(x, y, newSymbol)

			try {
				Thread.sleep(SERVER_TICK / 2);
				getMap().updateVisibleMap();
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
				getMap().updateVisibleStringMap();
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

	public void checkIfOver() {
		long remaining = actorList.parallelStream().filter(x -> x instanceof Enemy && x.isAlive()).count();
		System.out.println("There are " + remaining + " enemies remaining");
		if (remaining == 0)
			endGame();

	}

	private void endGame() {
		System.out.println("Ending game (but not really)");
		actorList.forEach(x -> x.isRunning = false);
		GameLogicController.isRunning = false;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

}