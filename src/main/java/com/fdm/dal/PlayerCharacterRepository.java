package com.fdm.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.fdm.model.PlayerCharacter;


@Repository
public interface PlayerCharacterRepository extends CrudRepository<PlayerCharacter, Integer> {

	List<PlayerCharacter> findByCharacterNameContainingAndCharacterSymbolContainingAndLevelContaining(String characterName,
			char characterSymbol, int level);
	
	List<PlayerCharacter> findByCharacterNameContainingAndCharacterSymbolAndLevelGreaterThan(String characterName,
			char characterSymbol, int level);
	
	List<PlayerCharacter> findByCharacterNameContainingAndCharacterSymbolAndLevelLessThan(String characterName,
			char characterSymbol, int level);	
	
	List<PlayerCharacter> findByCharacterNameContainingAndCharacterSymbolContainingAndLevelGreaterThan(String characterName,
			char characterSymbol, int level);

	List<PlayerCharacter> findByCharacterNameContainingAndCharacterSymbolContainingAndLevelLessThanAndKillCountLessThan(
			String name, char symbol, int level, int killCount);

	List<PlayerCharacter> findByCharacterNameContainingAndCharacterSymbolContainingAndLevelLessThanAndKillCountGreaterThan(
			String name, char symbol, int level, int killCount);

	List<PlayerCharacter> findByCharacterNameContainingAndCharacterSymbolContainingAndLevelGreaterThanAndKillCountLessThan(
			String name, char symbol, int level, int killCount);

	List<PlayerCharacter> findByCharacterNameContainingAndCharacterSymbolContainingAndLevelGreaterThanAndKillCountGreaterThan(
			String name, char symbol, int level, int killCount);

	List<PlayerCharacter> findByCharacterNameContainingAndLevelGreaterThanAndKillCountGreaterThan(String name,
			int level, int killCount);

	List<PlayerCharacter> findByCharacterNameContainingAndLevelGreaterThanAndKillCountLessThan(String name, int level,
			int killCount);

	List<PlayerCharacter> findByCharacterNameContainingAndLevelLessThanAndKillCountGreaterThan(String name, int level,
			int killCount);

	List<PlayerCharacter> findByCharacterNameContainingAndLevelLessThanAndKillCountLessThan(String name, int level,
			int killCount);
	
}
