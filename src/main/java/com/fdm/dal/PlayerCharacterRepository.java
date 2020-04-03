package com.fdm.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.fdm.model.PlayerCharacter;


@Repository
public interface PlayerCharacterRepository extends CrudRepository<PlayerCharacter, Integer> {
	
}
