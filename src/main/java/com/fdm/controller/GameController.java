package com.fdm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.dal.EnemyRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Actor;
import com.fdm.model.Enemy;
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

	Map map;
	boolean initialised = false;

	GameLogicController controller;

	@GetMapping({ "/home" })
	public String getIndex(HttpSession session) {
		if (session.getAttribute("account") != null) {
			return "home";
		}
		return "/home";
	}

	@MessageMapping("/game")
	public void updatePlayer(@ModelAttribute PlayerCharacter pc, @ModelAttribute char input) {
		pc.setInput(input);
	}

	public void initGame() {
		Iterable<Enemy> enemyIterator = enemyRepo.findAll();
		map = new Map("20x20test");
		List<Actor> actorList = new ArrayList<Actor>();
		for (Enemy enemy : enemyIterator) {
			actorList.add(enemy);
		}
		map.addActors(actorList);
		initialised = true;

		controller = new GameLogicController(map, actorList);
	}

	public boolean connect(@ModelAttribute PlayerCharacter pc) {
		pc.setMap(map);
		map.addActor(pc);
		pc.setMap(map);
		controller.addActor(pc);

		return true;
	}

	@PostMapping("/home")
	public char[][] postGame() {
//		Actor enemy = new Enemy("hi", 2, 4, key);
//		Actor enemy2 = new Enemy("bye",  5, 8, key);
//		Actor enemy3 = new Enemy("running out of things", 2, 4, key);
//		actorList.add(enemy);
//		actorList.add(enemy2);
//		actorList.add(enemy3);
//		PlayerCharacter playerCharacter = new PlayerCharacter("jim", 5, 5, key);
//		playerCharacter.setCharacterSymbol('µ');
//		playerCharacter.setMap(map);
//
//		actorList.add(playerCharacter);
		return controller.map.getMapCharacters();
	}

	@Autowired
	SimpMessagingTemplate template;

	@Scheduled(fixedDelay = GameLogicController.SERVER_TICK)
	public void autoUpdateMap() {
		if (initialised) {
			template.convertAndSend("/topic/game", controller.map.getMapCharacters());
		}
	}
}