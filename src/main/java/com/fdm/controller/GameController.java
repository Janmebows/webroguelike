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

	public boolean connect(@ModelAttribute PlayerCharacter pc) {
		controller = GameLogicController.getInstance();
		controller.addActor(pc);
		return true;
	}

	@PostMapping("/home")
	public String[][] postGame() {
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
		controller = GameLogicController.getInstance();
		if (controller.map != null)
//			return controller.map.getMapCharacters();
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
			template.convertAndSend("/topic/game", controller.map.getMapCharacters());
	}
}