package com.fdm.setup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fdm.controller.GameController;
import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Account;
import com.fdm.model.Actor;
import com.fdm.model.EnemyFactory;
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

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Populating database");
		Map map = new Map("20x20test");
		EnemyFactory ef = new EnemyFactory();
		List<Actor> actors = ef.makeEnemies(map,20);
		actorRepo.saveAll(actors);
		Account acc = new Account("uname", "pword");
	//	accountRepo.save(acc);
		PlayerCharacter plc = actorRepo.save(new PlayerCharacter("player1", 1, 1));
		plc.setCharacterSymbol('ñ');
		actorRepo.save(plc);
		actors.add(plc);
		acc.setPlayerCharacter(plc);
		accountRepo.save(acc);
		map.addActors(actors);
		GameController gc = new GameController(map, actors);
		Thread th = new Thread(gc);
		th.start();
		System.out.println("Finished populating");

	}
}
