package com.fdm.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Account;
import com.fdm.model.Enemy;
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
		actorRepo.save(new Enemy("enemy1", 1, 1));
		actorRepo.save(new Enemy("enemy2", 2, 1));
		actorRepo.save(new Enemy("enemy3", 1, 3));
		actorRepo.save(new Enemy("enemy41", 1, 1));
		actorRepo.save(new Enemy("enemy51", 10, -1));
		Account acc = new Account("uname", "pword");
	//	accountRepo.save(acc);
		PlayerCharacter plc = actorRepo.save(new PlayerCharacter("player1", 1, 1));
		acc.setPlayerCharacter(plc);
		accountRepo.save(acc);
//		Map map = mapRepo.save(new Map());
//		actorRepo.save(new Enemy("enemy1",map, 1, 1));
//		actorRepo.save(new Enemy("enemy2", 2, 1));
//		actorRepo.save(new Enemy("enemy3", 1, 3));
//		actorRepo.save(new Enemy("enemy41", 1, 1));
//		actorRepo.save(new Enemy("enemy51", 10, -1));
//		Account acc = new Account("uname", "pword");
//		accountRepo.save(acc);
//		PlayerCharacter plc = actorRepo.save(new PlayerCharacter("player1", 1, 1,acc));
//		acc.setPlayerCharacter(plc);
//		accountRepo.save(acc);
//		
		

		System.out.println("Finished populating");

	}
}
