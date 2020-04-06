package com.fdm.controller;

import java.util.List;

import com.fdm.model.Actor;
import com.fdm.model.Map;

public class GameController implements Runnable {

	public static final long SERVER_TICK = 2000;
	Map map;
	
	// if we want multiple maps this shouldn't be static
	public static boolean isRunning = true;
	private static volatile Object key;
	private List<Actor> actorList;

	public GameController(Map map, List<Actor> actorList) {
		super();
		this.map = map;
		GameController.key = key;
		this.actorList = actorList;
		actorList.forEach(x -> x.key = getKey());
	}
	



	public static Object getKey() {
		if (key == null)
			key = new Object();
		return key;
	}

	public void addActor(Actor newActor) {
		newActor.key = getKey();
		map.addActor(newActor);
		Thread th = new Thread(newActor);
		th.start();
	}

	public void handle() {
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

	public void handleNoPrint() {
		for (Actor actor : actorList) {
			Thread th = new Thread(actor);
			th.start();
		}
		while (isRunning) {

			synchronized (getKey()) {
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
//		handleNoPrint();
		handle();
	}

}
