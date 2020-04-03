package com.fdm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdm.dal.AccountRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Account;
import com.fdm.model.Actor;
import com.fdm.model.Enemy;
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
		System.out.println("Testing0");

		Map map = new Map("20x20test");
		Account account = new Account();
		Object key = new Object();
		PlayerCharacter playerCharacter = new PlayerCharacter("jim", 5, 5, key);
		System.out.println(playerCharacter);
		playerCharacter.setCharacterSymbol('µ');
		playerCharacter.setMap(map);
		Actor enemy = new Enemy("hi", 2, 4, key);
		Actor enemy2 = new Enemy("bye",  5, 8, key);
		Actor enemy3 = new Enemy("running out of things", 2, 4, key);
		List<Actor> actorList = new ArrayList<Actor>();
		actorList.add(enemy);
		actorList.add(enemy2);
		actorList.add(enemy3);
		actorList.add(playerCharacter);
		PlayerCharacterInputController pci = new PlayerCharacterInputController(playerCharacter);
		MapAndActorThreadController controller = new MapAndActorThreadController(map, pci, key, actorList);
		// controller.handle();
//		String[][] a = new String[][]();
//		Map m = controller.map;
//		for (int j = 0; j < m.getyMax(); ++j) {
//			for (int i = 0; i < m.getxMax(); ++i) {
//				a[i][j] = "";//(i, j);
//			}
//			System.out.println();
//		}
		return controller.map.getMapCharacters();
		//return controller.map;
	}
}
