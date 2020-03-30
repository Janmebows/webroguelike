package com.fdm.dal;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fdm.model.Map;


@Repository
public interface MapRepository extends CrudRepository<Map, Integer> {
	
}
