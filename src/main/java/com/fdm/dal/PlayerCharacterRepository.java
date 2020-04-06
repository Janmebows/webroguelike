package com.fdm.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.fdm.model.PlayerCharacter;


@Repository
public interface PlayerCharacterRepository extends CrudRepository<PlayerCharacter, Integer> {

	List<PlayerCharacter> findByNameContainingAndSymbolContainingAndLevelContaining(String characterName,
			char characterSymbol, int level);
	
	List<PlayerCharacter> findByNameContainingAndSymbolContainingAndLevelGreaterThan(String characterName,
			char characterSymbol, int level);
	
	List<PlayerCharacter> findByNameContainingAndSymbolContainingAndLevelLessThan(String characterName,
			char characterSymbol, int level);	
	
}
