package com.fdm.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdm.dal.AccountRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Actor;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class HomeController {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	MapRepository mapRepository;

	@GetMapping({ "/home" })
	public String getIndex(HttpSession session) {
		if (session.getAttribute("user") != null) {
			return "home";
		}
		return "/home";
	}

	@PostMapping("/home")
	public char[][] postGame() {
		Map map = new Map("20x20test");
		PlayerCharacter playerCharacter = new PlayerCharacter("jim", 5, 5);
		playerCharacter.setCharacterSymbol('µ');
		playerCharacter.setMap(map);
		MapAndActorThreadController controller = new MapAndActorThreadController(map, new PlayerCharacterInputController(playerCharacter), new Object(), new ArrayList<Actor>() );

		return controller.map.getMapCharacters();
	}
}
