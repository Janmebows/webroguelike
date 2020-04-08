package com.fdm.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.fdm.model.Enemy;

@Repository
public interface EnemyRepository extends CrudRepository<Enemy, Integer> {
	
}
