package com.fdm.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.dal.EnemyRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Actor;
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
		System.out.println("HIT HERE");
		//add player to the map
		if (session.getAttribute("account") != null) {
			return "game";
		}
		return "/game";

	}

	//@MessageMapping("/input")
	@PostMapping("/input")
	public void updatePlayer(@RequestBody PlayerCharacter playerCharacter) {
		Optional<Actor> a = actorRepo.findById(playerCharacter.getId());
		PlayerCharacter actualPlayerCharacter = (PlayerCharacter) a.get();
		actualPlayerCharacter.setInput(playerCharacter.nextInput);
		System.out.println(playerCharacter.nextInput);
		System.out.println("got input");
		
	}
//		
//			, @ModelAttribute Character input) {
//		System.out.println("got input");
//		if(pc == null)
//			System.out.println("Player was null (unsurprisingly)");
//		if(input == null)
//			System.out.println("input was null (thats bad)");
//		pc.setInput(input);
//	}

	public boolean connect(@ModelAttribute PlayerCharacter pc) {
		controller = GameLogicController.getInstance();
		controller.addActor(pc);
		return true;
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