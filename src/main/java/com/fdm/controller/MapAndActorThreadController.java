package com.fdm.controller;

import java.util.List;

import com.fdm.model.Actor;
import com.fdm.model.Map;

public class MapAndActorThreadController {

	private static final long SERVER_TICK = 2000;
	Map map;

	boolean isRunning = true;
	volatile Object key;
	private List<Actor> actorList;
	
	//can make this a list when needed
	PlayerCharacterInputController pci;


	public MapAndActorThreadController(Map map, PlayerCharacterInputController pci, Object key, List<Actor> actorList) {
		super();
		this.map = map;
		this.pci = pci;
		this.key = key;
		this.actorList = actorList;
		map.addActors(actorList);
	}

	public void handle() {
		map.printMap();
		for (Actor enemy : actorList) {
			Thread th = new Thread(enemy);
			th.start();
		}

		Thread inputThread = new Thread(pci);
		inputThread.start();
		while (isRunning) {

			synchronized (key) {
				key.notifyAll();
			}
				map.printMap();
				// MapUpdate(x, y, newSymbol)
			
			try {
				Thread.sleep(SERVER_TICK);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
