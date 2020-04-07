package com.fdm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.dal.PlayerCharacterRepository;
import com.fdm.model.PlayerCharacter;
import com.fdm.model.SearchFilters;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class SearchController {
	
	@Autowired
	PlayerCharacterRepository playerCharacterRepository;
	
	@GetMapping("/search")
	public String getSearchPage() {
		return "search";
	}
	
	@PostMapping("/search")
	public List<PlayerCharacter> processSearch(@RequestBody SearchFilters searchInput) {
		
		System.out.println("processSearch init: "+ searchInput.getName()+" " + searchInput.getSymbol() + " " + searchInput.getLevel());

		List<PlayerCharacter> foundPlayers = new ArrayList<PlayerCharacter>();
		//name, symbol, level, 
		
		foundPlayers = playerCharacterRepository.findByCharacterNameContainingAndCharacterSymbolAndLevelGreaterThan(searchInput.getName(), searchInput.getSymbol(), searchInput.getLevel());
		
		return foundPlayers;
	}

}
