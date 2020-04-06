package com.fdm.controller;

import java.util.List;

import com.fdm.model.Actor;
import com.fdm.model.Map;

public class MapAndActorThreadController {

	public static final long SERVER_TICK = 2000;
	Map map;

	// if we want multiple maps this shouldn't be static
	public static boolean isRunning = true;
	volatile Object key;
	private List<Actor> actorList;

	public MapAndActorThreadController(Map map, Object key, List<Actor> actorList) {
		super();
		this.map = map;
		this.key = key;
		this.actorList = actorList;
	}

	public void addActor(Actor newActor) {
		newActor.key = this.key;
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

			synchronized (key) {
				key.notifyAll();
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
		//wake up threads so they can die
		synchronized (key) {
			key.notifyAll();
		}
	}

}
