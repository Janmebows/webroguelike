package com.fdm.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.dal.EnemyRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Account;
import com.fdm.model.Actor;
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

	@Autowired
	ActorFactory af;

	Map map;
	GameLogicController controller;

	@GetMapping({ "/game" })
	public String getIndex(HttpSession session) {
		System.out.println("HIT HERE");
		// add player to the map
		if (session.getAttribute("account") != null) {
			return "game";
		}
		return "/game";

	}

	// @MessageMapping("/input")
	@PostMapping("/input")
	public void updatePlayer(@RequestBody PlayerCharacter playerCharacter) {
		controller = GameLogicController.getInstance();
		PlayerCharacter actualPlayerCharacter = (PlayerCharacter) controller.findActor(playerCharacter.getId());
		actualPlayerCharacter.setInput(playerCharacter.nextInput);
		controller.tryAddActor(actualPlayerCharacter);

		System.out.println(playerCharacter.nextInput);
		System.out.println("got input from player " + actualPlayerCharacter.getCharacterName() + " id "
				+ actualPlayerCharacter.getId());
		actorRepo.save(actualPlayerCharacter);
	}

	@PostMapping("/leaveGame")
	public void disconnect(@RequestBody PlayerCharacter pc) {
		controller = GameLogicController.getInstance();
		controller.removeActor(pc);
	}

	@PostMapping("/joinGame")
	public Account connect(@RequestBody Account acc) {
		Optional<Account> _account = accountRepository.findById(acc.getId());
		acc = _account.get();
		PlayerCharacter pc = acc.getPlayerCharacter();
		controller = GameLogicController.getInstance();
		if (controller == null) {
			// start new game
			map = new Map("20x20test");
			controller = new GameLogicController(map, af.makeEnemies(map, 20));
		}
		if (pc == null || pc.getId() == 0) {
			pc = af.makePlayerCharacter(acc.getUsername(), controller.map);
		} else {
			pc = (PlayerCharacter) actorRepo.findById(pc.getId()).get();
//			controller.addActor(pc);
		}

		System.out.println("A player tried to join");
		System.out.println(pc);
		controller.tryAddActor(pc);
		pc = actorRepo.save(pc);
		acc.setPlayerCharacter(pc);
		acc = accountRepository.save(acc);
		return acc;

	}

	@PostMapping("/game")
	public String[][] postGame() {

		controller = GameLogicController.getInstance();
		if (controller.map != null)
			return controller.map.getStringMap();

		else
			return null;
	}

	@Autowired
	SimpMessagingTemplate template;

	@Scheduled(fixedDelay = GameLogicController.SERVER_TICK)
	public void autoUpdateMap() {
		controller = GameLogicController.getInstance();
		if (controller.map != null)
			template.convertAndSend("/topic/game", controller.map.getStringMap());
	}
}