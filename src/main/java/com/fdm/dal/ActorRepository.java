package com.fdm.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fdm.model.Account;
import com.fdm.model.Actor;


@Repository
public interface ActorRepository extends CrudRepository<Actor, Integer> {
	
}
