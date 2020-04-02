package com.fdm.controller;

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
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

import PlaceholderTesting.IView;
import PlaceholderTesting.View;

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
		return "index";
	}
	
	@PostMapping("/home")
	public String postLogin(HttpSession session, @RequestBody Account account1) {
		System.out.println("Testing0");

		IView iView = new View();
		Map map = new Map("20x20test");
		
		System.out.println(map);
		Account account = new Account();
		PlayerCharacter playerCharacter = new PlayerCharacter("jim", 5, 5);
		System.out.println(playerCharacter);
		playerCharacter.setCharacterSymbol('µ');
		playerCharacter.setMap(map);
		
		PlayerMovementController controller = new PlayerMovementController(map, playerCharacter, iView);
		controller.handle();
		System.out.println("Testing1");
		
		return "/";
	}
}
