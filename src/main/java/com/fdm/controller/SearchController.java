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
import com.fdm.model.LevelSorter;
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
		
		System.out.println("processSearch init: "+ searchInput.getName()+ " " + searchInput.getLevel() + " " + searchInput.getKillCount());

		List<PlayerCharacter> foundPlayers = new ArrayList<PlayerCharacter>();
		//name, level, killCount
		
		if(searchInput.getLevelDirection().equals("greaterThan") && searchInput.getKillDirection().equals("greaterThan")) {
			foundPlayers = playerCharacterRepository.findByCharacterNameContainingAndLevelGreaterThanAndKillCountGreaterThan(searchInput.getName(), searchInput.getLevel(), searchInput.getKillCount());
		} else if(searchInput.getLevelDirection().equals("greaterThan") && searchInput.getKillDirection().equals("lessThan")) {
			foundPlayers = playerCharacterRepository.findByCharacterNameContainingAndLevelGreaterThanAndKillCountLessThan(searchInput.getName(), searchInput.getLevel(), searchInput.getKillCount());
		} else if(searchInput.getLevelDirection().equals("lessThan") && searchInput.getKillDirection().equals("greaterThan")) {
			foundPlayers = playerCharacterRepository.findByCharacterNameContainingAndLevelLessThanAndKillCountGreaterThan(searchInput.getName(), searchInput.getLevel(), searchInput.getKillCount());
		} else if(searchInput.getLevelDirection().equals("lessThan") && searchInput.getKillDirection().equals("lessThan")) {
			foundPlayers = playerCharacterRepository.findByCharacterNameContainingAndLevelLessThanAndKillCountLessThan(searchInput.getName(), searchInput.getLevel(), searchInput.getKillCount());
		} 
		
		foundPlayers.sort(new LevelSorter());
		
		System.out.println(foundPlayers);
		return foundPlayers;
	}
	
	@PostMapping("/findAll")
	public List<PlayerCharacter> findAllPlayers() {
		List<PlayerCharacter> foundPlayers = (List<PlayerCharacter>) playerCharacterRepository.findAll();
		
		foundPlayers.sort(new LevelSorter());
		return foundPlayers;
		
	}

}
