package com.fdm.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.dal.AccountRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Account;
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
	public Map postGame() {
		System.out.println("Testing0");

		Map map = new Map("20x20test");
		Account account = new Account();
		PlayerCharacter playerCharacter = new PlayerCharacter("jim", 5, 5);
		System.out.println(playerCharacter);
		playerCharacter.setCharacterSymbol('µ');
		playerCharacter.setMap(map);

		MapAndActorThreadController controller = new MapAndActorThreadController(map, new PlayerCharacterInputController(playerCharacter), playerCharacter, new ArrayList<Actor>() );
		// controller.handle();
//		String[][] a = new String[][]();
//		Map m = controller.map;
//		for (int j = 0; j < m.getyMax(); ++j) {
//			for (int i = 0; i < m.getxMax(); ++i) {
//				a[i][j] = "";//(i, j);
//			}
//			System.out.println();
//		}

		return controller.map;
	}
}
