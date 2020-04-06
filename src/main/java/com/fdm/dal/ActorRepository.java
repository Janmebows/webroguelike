package com.fdm.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.fdm.model.Actor;


@Repository
public interface ActorRepository extends CrudRepository<Actor, Integer> {
	
}
