package com.fdm.setup;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fdm.controller.GameLogicController;
import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Account;
import com.fdm.model.Actor;
import com.fdm.model.ActorFactory;
import com.fdm.model.Map;

/**
 * Class to load in data on initialisation of the server
 * @author KILA
 * @version 1.0
 *
 */
@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	AccountRepository accountRepo;
	@Autowired
	ActorRepository actorRepo;
	@Autowired
	MapRepository mapRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Logger.getLogger("DataLoaderLogger").info("Started populating database");
		//make a few accounts
		Map map = new Map("ProductionMap");
		List<Actor> actors = ActorFactory.makeEnemies(map,10);
		actorRepo.saveAll(actors);
		Account acc = new Account("uname", "pword");
		Account acctwo = new Account("u", "p");
		accountRepo.save(acctwo);
		accountRepo.save(acc);
		map.addActors(actors);
		map = mapRepo.save(map);
		GameLogicController gc = new GameLogicController(map, actors);
		Thread th = new Thread(gc);
		th.start();

		Logger.getLogger("DataLoaderLogger").info("Finished populating the database");
	}
}
