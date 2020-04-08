package com.fdm.setup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fdm.controller.GameController;
import com.fdm.controller.GameLogicController;
import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Account;
import com.fdm.model.Actor;
import com.fdm.model.ActorFactory;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	AccountRepository accountRepo;
	@Autowired
	ActorRepository actorRepo;
	@Autowired
	MapRepository mapRepo;

	  @Autowired
	  ActorFactory actorFactory;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//make a few accounts
		//make some with characters
		System.out.println("Populating database");
		Map map = new Map("ProductionMap");
		List<Actor> actors = actorFactory.makeEnemies(map,10);
		actorRepo.saveAll(actors);
		Account acc = new Account("uname", "pword");
		Account acctwo = new Account("u", "p");
//		acctwo.setPlayerCharacter(actorFactory.makePlayerCharacter(acctwo.getUsername(),map));
		accountRepo.save(acctwo);
	//	accountRepo.save(acc);
		// PlayerCharacter("player1", 1, 1));
//		plc.setCharacterSymbol('ñ');
//		plc.setColor(255, 0, 0);
//		actorRepo.save(plc);
//
//		acc.setPlayerCharacter(plc);
		accountRepo.save(acc);
		map.addActors(actors);
		map = mapRepo.save(map);
		GameLogicController gc = new GameLogicController(map, actors);
//		actorRepo.saveAll(actors);
		Thread th = new Thread(gc);
		th.start();
		System.out.println("Finished populating");

	}
}
