package com.fdm.controller;

import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.dal.EnemyRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Account;
import com.fdm.model.ActorFactory;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class GameController {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ActorRepository actorRepo;
	@Autowired
	EnemyRepository enemyRepo;
	@Autowired
	MapRepository mapRepo;

	protected transient static Logger logger = Logger.getLogger("GameControllerLogger");


	Map map;
	GameLogicController controller;

	@PostMapping("/input")
	public void updatePlayer(@RequestBody PlayerCharacter playerCharacter) {
		controller = GameLogicController.getInstance();
		PlayerCharacter actualPlayerCharacter = (PlayerCharacter) controller.findActor(playerCharacter.getId());

		if(actualPlayerCharacter==null) {
			actualPlayerCharacter = (PlayerCharacter) actorRepo.findById(playerCharacter.getId()).get();
			controller.addActor(actualPlayerCharacter);
			logger.warn("Player tried to move but did not exist");
		}
		actualPlayerCharacter.setInput(playerCharacter.nextInput);


		logger.trace("got input from player " + actualPlayerCharacter.getCharacterName() + " id "
				+ actualPlayerCharacter.getId()+ " position " + actualPlayerCharacter.getX() + "," + actualPlayerCharacter.getY());
		actorRepo.save(actualPlayerCharacter);
	}

	@PostMapping("/leaveGame")
	public void disconnect(@RequestBody PlayerCharacter pc) {
		controller = GameLogicController.getInstance();
		controller.removeActor(pc);
		logger.info("Disconnected player " + pc);
	}

	@PostMapping("/joinGame")
	public Account connect(@RequestBody Account acc) {
		logger.info("Account attempting to connect " + acc.getId());
		Optional<Account> _account = accountRepository.findById(acc.getId());

		if(!_account.isPresent()) {
			logger.error("Account not found");

			return null;
		}
		acc = _account.get();
		PlayerCharacter pc = acc.getPlayerCharacter();
		controller = GameLogicController.getInstance();
		if (controller == null) {
			// start new game
			logger.error("Had to start a new game");
			map = new Map("20x20test");
			controller = new GameLogicController(map, ActorFactory.makeEnemies(map, 20));
		}
		if (pc == null || pc.getId() == 0) {

			logger.error("Player did not exist");
			pc = ActorFactory.makePlayerCharacter(acc.getUsername(), controller.getMap());
			acc.setPlayerCharacter(pc);
		} else {
			pc = (PlayerCharacter) actorRepo.findById(pc.getId()).get();
		}

		controller.tryAddActor(pc);
		pc = actorRepo.save(pc);
		logger.info("Player joined with id " + pc.getId());
		acc.setPlayerCharacter(pc);
		acc = accountRepository.save(acc);
		return acc;

	}

	@PostMapping("/game")
	public String[][] postGame() {
		logger.trace("Drew initial map");

		controller = GameLogicController.getInstance();
		if (controller.getMap() != null)
			return controller.getMap().getStringMap();
		else
			return null;
	}

	@Autowired
	SimpMessagingTemplate template;

	@Scheduled(fixedDelay = GameLogicController.SERVER_TICK)
	public void autoUpdateMap() {
		controller = GameLogicController.getInstance();

		if (controller.getMap() != null)
			template.convertAndSend("/topic/game", controller.getMap().getStringMap());
	}
}