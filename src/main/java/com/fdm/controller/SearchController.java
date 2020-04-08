package com.fdm.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
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
	protected transient static Logger logger = Logger.getLogger("SearchLogger");
	@Autowired
	PlayerCharacterRepository playerCharacterRepository;

	@GetMapping("/search")
	public String getSearchPage() {
		return "search";
	}

	@PostMapping("/search")
	public List<PlayerCharacter> processSearch(@RequestBody SearchFilters searchInput) {
		logger.trace("A search was made with " + searchInput.getName() + " " + searchInput.getLevel() + " "
				+ searchInput.getKillCount() + " " + searchInput.getKillDirection() + " "
				+ searchInput.getLevelDirection());

		List<PlayerCharacter> foundPlayers = new ArrayList<PlayerCharacter>();
		// name, level, killCount
		if (searchInput.getLevelDirection().equals("greaterThan")
				&& searchInput.getKillDirection().equals("greaterThan")) {
			foundPlayers = playerCharacterRepository
					.findByCharacterNameContainingAndLevelGreaterThan(searchInput.getName(), searchInput.getLevel());
			System.out.println("Hello there" + foundPlayers);
		} else if (searchInput.getLevelDirection().equals("greaterThan")
				&& searchInput.getKillDirection().equals("lessThan")) {
			foundPlayers = playerCharacterRepository
					.findByCharacterNameContainingAndLevelGreaterThanAndKillCountLessThan(searchInput.getName(),
							searchInput.getLevel(), searchInput.getKillCount());
		} else if (searchInput.getLevelDirection().equals("lessThan")
				&& searchInput.getKillDirection().equals("greaterThan")) {
			foundPlayers = playerCharacterRepository
					.findByCharacterNameContainingAndLevelLessThanAndKillCountGreaterThan(searchInput.getName(),
							searchInput.getLevel(), searchInput.getKillCount());
		} else if (searchInput.getLevelDirection().equals("lessThan")
				&& searchInput.getKillDirection().equals("lessThan")) {
			foundPlayers = playerCharacterRepository.findByCharacterNameContainingAndLevelLessThanAndKillCountLessThan(
					searchInput.getName(), searchInput.getLevel(), searchInput.getKillCount());
		}
		foundPlayers.sort(new LevelSorter());
		logger.info("Found " + foundPlayers);
		return foundPlayers;
	}

	@PostMapping("/findAll")
	public List<PlayerCharacter> findAllPlayers() {
		logger.info("Searched for all players");
		List<PlayerCharacter> foundPlayers = (List<PlayerCharacter>) playerCharacterRepository.findAll();

		foundPlayers.sort(new LevelSorter());
		return foundPlayers;

	}

}
