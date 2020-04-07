package com.fdm.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.dal.EnemyRepository;
import com.fdm.dal.MapRepository;
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

	@GetMapping({ "/game" })
	public String getIndex(HttpSession session) {
		if (session.getAttribute("account") != null) {
			return "game";
		}
		return "/game";
	}

	@MessageMapping("/game")
	@SendTo("/topic/game")
	public void updatePlayer(@ModelAttribute PlayerCharacter pc, @ModelAttribute char input) {
		pc.setInput(input);
	}

	public boolean connect(@ModelAttribute PlayerCharacter pc) {
		controller = GameLogicController.getInstance();
		controller.addActor(pc);
		return true;
	}

	@PostMapping("/game")
	public char[][] postGame() {

		controller = GameLogicController.getInstance();
		if (controller.map != null)
			return controller.map.getMapCharacters();
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